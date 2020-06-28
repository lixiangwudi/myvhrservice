package org.javaboy.vhr.controller;
/**
 * @Author lixiang
 * @Date 2020/5/21 19:51
 * @Version 1.0
 */

import org.javaboy.vhr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author     ：lx
 * @date       ：Created in 2020/5/21 19:51
 * @description：
 * @modified By：
 * @version: 1.0
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录");
    }
}
