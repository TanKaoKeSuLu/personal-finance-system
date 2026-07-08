package com.tankaokesulu.financeapi.exception;

import com.tankaokesulu.financeapi.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有运行时异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<String> business(BusinessException e){

        return Result.error(e.getMessage());

    }

    /**
     * 处理所有未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exception(Exception e){

        e.printStackTrace();

        return Result.error("服务器异常");

    }

}