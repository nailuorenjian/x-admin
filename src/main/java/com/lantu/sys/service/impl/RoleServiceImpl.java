package com.lantu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.sys.entity.Role;
import com.lantu.sys.entity.RoleMenu;
import com.lantu.sys.mapper.RoleMapper;
import com.lantu.sys.mapper.RoleMenuMapper;
import com.lantu.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Override
    @Transactional //多表注解
    public void addRole(Role role) {
        // 写入角色表
        this.baseMapper.insert(role);
        // 写入 角色和菜单关系表
        if( null != role.getMenuIdList()){
            for (Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
            }
        }

    }

    @Override
    public Role getUserById(Integer id) {
        Role role = this.baseMapper.selectById(id);
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        // 修改角色表
        this.baseMapper.updateById(role);
        // 删除原有权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getRoleId());
        roleMenuMapper.delete(wrapper);
        // 新增权限
        // 写入 角色和菜单关系表
        if( null != role.getMenuIdList()){
            for (Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
            }
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        // 删除角色表
        this.baseMapper.deleteById(id);
        // 删除原有权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuMapper.delete(wrapper);
    }
}
