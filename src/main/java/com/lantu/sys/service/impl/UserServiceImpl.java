package com.lantu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.common.vo.utils.JwtUtils;
import com.lantu.sys.entity.Menu;
import com.lantu.sys.entity.User;
import com.lantu.sys.entity.UserRole;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.mapper.UserRoleMapper;
import com.lantu.sys.service.IMenuService;
import com.lantu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IMenuService menuService;

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名和密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        // wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);

        //结果不为空，并且密码和传入的密码匹配， 则生成token，并将用户信息存入redis
        if (loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())){

            // 暂时使用uuid，终极方案是jwt
//            String key = "user:" + UUID.randomUUID();

            //存入redis
            loginUser.setPassword(null); //密码不存入redis
//            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES); //保存30minutes

            // 创建jwt
            String token = jwtUtils.createToken(loginUser);

            //返回数据
            Map<String, Object> data = new HashMap<>();
//            data.put("token",key);
            data.put("token",token);

            return data;
        }

        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 根据token，获取redis的登录信息
//        Object obj = redisTemplate.opsForValue().get(token);

        User loginUser = null;
        try {
             loginUser = jwtUtils.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(loginUser != null){
            // json数据反序列化 JSON.toJSONString(obj) json字符串 反序列化成User对象
//            User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar()); //头像

            //角色
            List<String> roleList =
                    this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);

            //权限列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList",menuList);

            return data;
        }

        return null;
    }

    @Override
    public void logout(String token) {
      //  redisTemplate.delete(token);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        //写入用户表
        this.baseMapper.insert(user);
        //写入角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList != null){
            for (Integer roleId : roleIdList){
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }

    }

    @Override
    @Transactional
    public User getUserById(Integer id) {
        User user = this.baseMapper.selectById(id);
        // 角色列表的查询
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);

        List<Integer> roleIdList = userRoleList.stream()
                                    .map(userRole -> {return userRole.getRoleId();})
                                    .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        //更新用户表
        this.baseMapper.updateById(user);
        //清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(wrapper);
        //设置新的角色
        //写入角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList != null){
            for (Integer roleId : roleIdList){
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }


    }

    @Override
    public void deleteUserById(Integer id) {
        this.baseMapper.deleteById(id);
        //清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(wrapper);

    }
}
