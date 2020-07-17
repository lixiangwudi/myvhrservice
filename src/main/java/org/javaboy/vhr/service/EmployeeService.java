package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeMapper;
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
    EmployeeMapper employeeMapper;

    public RespbeanPage getEmployeeByPage(Integer page, Integer size,String word) {
        if(page != null && size != null){
            page = (page -1) * size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, word);
        Long total = employeeMapper.getTotal(word);
        RespbeanPage bean = new RespbeanPage();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    public Integer getMaxWorkId() {
        return employeeMapper.getMaxWorkId();
    }

    public Integer deleteEmployeeById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer deleteEmployeeByIds(Integer[] ids) {
        return employeeMapper.deleteEmployeeByIds(ids);
    }

    public Integer editEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
}
