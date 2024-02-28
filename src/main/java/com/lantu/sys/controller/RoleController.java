package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.Role;
import com.lantu.sys.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    public Result<Map<String,Object>> getRoleList(@RequestParam(value = "roleName",required = false) String roleName,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasLength(roleName), Role::getRoleName,roleName);
        wrapper.orderByDesc(Role::getRoleId);

        Page<Role> page = new Page<>(pageNo,pageSize);
        roleService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }


    @PostMapping
    // 前端是json数据，需要用@RequestBody来转换
    public Result<?> addRole(@RequestBody Role role){
        roleService.addRole(role);
        return Result.success("新增role成功");
    }

    @PutMapping
    // 修改role info
    public Result<?> updateRole(@RequestBody Role role){
        roleService.updateRole(role);
        return Result.success("修改role成功");
    }

    // 查询role info
    @GetMapping("/{id}")
    public Result<Role> getUserById(@PathVariable("id") Integer id){
        Role role = roleService.getUserById(id);
        return Result.success(role);
    }

    @DeleteMapping("/{id}")
    public Result<Role> deleteUserById(@PathVariable("id") Integer id){
        roleService.deleteUserById(id);
        return Result.success("delete role info");
    }

    @GetMapping("/all")
    public Result<List<Role>> getAllRole(){
        List<Role> roleList = roleService.list();
        return Result.success(roleList);
    }
}
