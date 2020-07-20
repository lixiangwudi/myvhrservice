package org.javaboy.vhr.controller.empbasic;

import com.sun.javafx.binding.StringFormatter;
import org.javaboy.vhr.mapper.RespbeanPage;
import org.javaboy.vhr.model.*;
import org.javaboy.vhr.service.*;
import org.javaboy.vhr.utils.PoiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author lixiang
 * @Date 2020/7/12 16:44
 * @Version 1.0
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    NationService nationService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;

    SimpleDateFormat year = new SimpleDateFormat("yyyy");

    SimpleDateFormat month = new SimpleDateFormat("MM");





    @GetMapping("/")
    public RespbeanPage getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,String keyword) {
        return employeeService.getEmployeeByPage(page, size, keyword);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double ye = Double.parseDouble(year.format(endContract)) - Double.parseDouble(year.format(beginContract))  ;
        double mon = (Double.parseDouble(month.format(endContract)) - Double.parseDouble(month.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(String.format("%.2f",ye*12+mon)));
        if(employeeService.addEmp(employee) == 1){
            return RespBean.ok("添加成功");
        }else{
            return RespBean.error("添加失败");
        }
    }

    @PutMapping("/")
    public RespBean editEmp(@RequestBody Employee employee){
        if(employeeService.editEmp(employee) == 1){
            return RespBean.ok("添加成功");
        }else{
            return RespBean.error("添加失败");
        }
    }



    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }


    @GetMapping("/maxWorkId")
    public RespBean getMaxWordId(){
        RespBean build = RespBean.build().setStatus(200).setObj(String.format("%08d",employeeService.getMaxWorkId()+1));
        return build;
    }


    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeById(@PathVariable Integer id){
        return employeeService.deleteEmployeeById(id) == 1?RespBean.ok("删除成功"):RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteEmployeeByIds(Integer[] ids){
        return employeeService.deleteEmployeeByIds(ids) == ids.length?RespBean.ok("删除成功"):RespBean.error("删除失败");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        List<Employee> employeeByPage = (List<Employee>) employeeService.getEmployeeByPage(null, null, null).getData();
        return PoiUtils.export2Excel(employeeByPage);
    }

}
