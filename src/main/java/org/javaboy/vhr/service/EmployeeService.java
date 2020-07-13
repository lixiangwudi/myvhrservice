package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeecMapper;
import org.javaboy.vhr.mapper.RespbeanPage;
import org.javaboy.vhr.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lixiang
 * @Date 2020/7/12 16:52
 * @Version 1.0
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeecMapper employeeecMapper;
    public RespbeanPage getEmployeeByPage(Integer page, Integer size) {
        if(page != null && size != null){
            page = (page -1) * size;
        }
        List<Employee> data = employeeecMapper.getEmployeeByPage(page, size);
        Long total = employeeecMapper.getTotal();
        RespbeanPage bean = new RespbeanPage();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
}
