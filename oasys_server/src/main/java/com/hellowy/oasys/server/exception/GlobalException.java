package com.hellowy.oasys.server.exception;

//import com.hellowy.oasys.server.pojo.RespBean;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.sql.SQLException;
//import java.sql.SQLIntegrityConstraintViolationException;
//
///**
// * 全局异常处理(配置该类会导致图形验证码失效 暂时禁用)
// *
// * @author wondercar
// * @date 2021/2/3
// */
//@RestControllerAdvice
//public class GlobalException {
//
//    @ExceptionHandler(Exception.class)
//    public RespBean mySQLException(Exception e) {
//        //sql表数据关联异常
//        if (e instanceof SQLIntegrityConstraintViolationException) {
//            return RespBean.error("该数据已和其他数据表关联，操作失败！");
//        }
//        //SQL异常
//        if (e instanceof SQLException) {
//            return RespBean.error("SQL异常，操作失败！");
//        }
//        return RespBean.error("操作异常，请重试！");
//    }
//}
