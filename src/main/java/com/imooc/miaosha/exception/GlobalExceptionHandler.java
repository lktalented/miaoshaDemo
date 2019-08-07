package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 同一异常处理类
 * luokai
 * 2019/1/8 0008 下午 4:26
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<Object> ExceptionHandler(Exception e){
        //e.printStackTrace();
        if (e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getMsg()) ;
        }else if (e instanceof BindException){
            BindException ex = (BindException)e;
            List<ObjectError> allErrors = ex.getAllErrors();
            ObjectError objectError = allErrors.get(0);
            String message = objectError.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));

        }else if (e instanceof AuthenticationException){
            AuthenticationException ex = (AuthenticationException)e;
            return Result.error(new CodeMsg("用户名或密码错误！"));

        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }

}
