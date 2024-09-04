package com.leyili.exception;


import com.leyili.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/*
 * 全局异常处理器
 * */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public Result ex(DuplicateKeyException ex){
         ex.printStackTrace();
         return Result.error("操作失败,添加的对象已存在");
    }

    @ExceptionHandler(CustomException.class)
    public Result ex(CustomException ex){
        ex.printStackTrace();

        return Result.error(ex.getMessage());
    }

}
