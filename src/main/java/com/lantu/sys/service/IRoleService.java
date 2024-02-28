package com.lantu.sys.service;

import com.lantu.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
public interface IRoleService extends IService<Role> {

    void addRole(Role role);

    Role getUserById(Integer id);

    void updateRole(Role role);

    void deleteUserById(Integer id);
}
