package com.agger.validatortest.controller;

import com.agger.validatortest.vo.ResultVO;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 全局异常处理
 * @author: zjn
 * @create: 2022-05-17 17:00
 **/
@RestControllerAdvice
public class GlobalExceptionHandlerController {

    /**
     * @Title: handleConstraintViolationException
     * @Description: Get方式参数验证异常
     * @author: zjn
     * @create: 2022-05-17 17:00
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultVO handleConstraintViolationException(ConstraintViolationException ex) throws IOException {
        // 获取所有错误信息
        HashSet<ConstraintViolation<?>> set = (HashSet<ConstraintViolation<?>>) ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = set.iterator();
        if(iterator.hasNext()){
            ConstraintViolation<?> next = iterator.next();
            String msg = next.getMessageTemplate();
            //返回自定义信息格式
            return new ResultVO(-1,msg);
        }
        return new ResultVO(-1,"参数错误");
    }

    /**
     * @Title: handleConstraintViolationException
     * @Description: Post方式参数验证异常
     * @author: zjn
     * @create: 2022-05-17 17:00
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVO handleConstraintViolationException(MethodArgumentNotValidException ex) throws IOException {
        //获取所有错误异常
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        //只返回第一个信息
        ObjectError error = allErrors.get(0);
        //返回自定义信息格式
        return new ResultVO(-1,error.getDefaultMessage());
    }


}