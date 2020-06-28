package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.RoleMapper;
import org.javaboy.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lixiang
 * @Date 2020/6/9 23:09
 * @Version 1.0
 */
@Service
public class PermissionService {
    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }


    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
