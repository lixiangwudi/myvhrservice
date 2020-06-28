package org.javaboy.vhr.service;
/**
 * @Author lixiang
 * @Date 2020/5/24 11:20
 * @Version 1.0
 */

import org.javaboy.vhr.mapper.MenuMapper;
import org.javaboy.vhr.mapper.MenuRoleMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author     ：lx
 * @date       ：Created in 2020/5/24 11:20
 * @description：
 * @modified By：
 * @version: 1.0
 */
@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return  menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public Boolean updateMidsByRid(Integer rid,Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        return mids.length == menuRoleMapper.insertMidsByRid(rid,mids);
    }
}
