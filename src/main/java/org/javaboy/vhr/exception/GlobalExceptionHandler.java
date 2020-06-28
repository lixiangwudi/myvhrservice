package org.javaboy.vhr.exception;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.javaboy.vhr.model.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @Author lixiang
 * @Date 2020/6/7 10:46
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RespBean sqlExceptionHandler(SQLException e){
        if(e instanceof MySQLIntegrityConstraintViolationException){
            return RespBean.error("该职位有关联关系，无法删除");
        }
        return RespBean.error("数据库异常，操作失败");
    }
}
