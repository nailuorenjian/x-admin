package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.User;
import com.lantu.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
@Api(tags = {"user"})
@RestController // json数据使用RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Result.success(list,"success");
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if (data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"username or pw wrong");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        //根据 token获取用户信息，从redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"login info failed,login again");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                               @RequestParam(value = "phone",required = false) String phone,
                                               @RequestParam(value = "pageNo") Long pageNo,
                                               @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasLength(username), User::getUsername,username);
        wrapper.like(StringUtils.hasLength(phone), User::getPhone,phone);

        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }


    @PostMapping
    // 前端是json数据，需要用@RequestBody来转换
    public Result<?> addUser(@RequestBody User user){
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    @PutMapping
    // 修改user info
    public Result<?> updateUser(@RequestBody User user){
        // 密码加密
        user.setPassword(null);
        userService.updateUser(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return Result.success("delete user info");
    }
}
