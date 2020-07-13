package org.javaboy.vhr.controller.system;

import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.Role;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    HrService hrService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){

        if(hrService.updateHr(hr) == 1){
            return RespBean.ok("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return hrService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRoles(Integer hrid,Integer[] rids){
        if(hrService.updateHrRoles(hrid,rids)){
            return RespBean.ok("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id){
        if(hrService.deleteHrById(id) == 1){
            return RespBean.ok("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
}
