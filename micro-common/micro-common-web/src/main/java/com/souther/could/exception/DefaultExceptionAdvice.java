package com.souther.could.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Description
 * @Author souther
 * @Date 2020/5/14 14:05
 * @Version 1.0
 **/
@Slf4j
public class DefaultExceptionAdvice {

  private static final String ARGUMENT_NOT_VALID = "参数[%s]校验失败：%s";

  // 字段校验
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    FieldError firstError = bindingResult.getFieldErrors().get(0);
    String info = String.format(ARGUMENT_NOT_VALID,
        firstError.getField(), firstError.getDefaultMessage());
    return Result.error(ResultCode.FRONT_PARAM, info);
  }

  // 请求参数缺失
  @ExceptionHandler(BindException.class)
  public Result<String> bindExceptionException(BindException e) {
    List<ObjectError> errors = e.getBindingResult().getAllErrors();
    ObjectError handleError = errors.get(0);
    String info = String.format(ARGUMENT_NOT_VALID,
        handleError.getCodes()[1], handleError.getDefaultMessage());
    return Result.error(ResultCode.FRONT_PARAM, info);
  }

  // 请求参数缺失
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public Result<String> missingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    String info = "缺少参数：" + e.getParameterName();
    return Result.error(ResultCode.FRONT_PARAM, info);
  }

  // 请求参数缺失
  @ExceptionHandler(MyBindException.class)
  public Result<String> MyBindException(
      MyBindException e) {
    String info = "缺少参数：" + e.getMessage();
    return Result.error(ResultCode.FRONT_PARAM, info);
  }

  // 请求方式错误
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public Result<String> httpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    String info = "请求方式错误:" + e.getMethod();
    return Result.error(ResultCode.FRONT_PARAM, info);
  }

  // 自定义异常
  @ExceptionHandler(MyException.class)
  public Result<String> myException(
      MyException e) {
    String info = e.getMessage();
    if (e.getCode().equals("500")) {
      log.error(info);
    }
    return Result.error(e.getCode(), info);
  }

  // 默认异常处理
  @ExceptionHandler(Exception.class)
  public Result<String> defaultException(Exception e) {
    log.error("");
    log.error("*******************bugInfo begin*******************");
    log.error(e.toString());
    StackTraceElement[] stackTrace = e.getStackTrace();
    for (StackTraceElement item : stackTrace) {
      log.error(item.toString());
    }
    log.error("*******************bugInfo end*******************");
    return Result.error();
  }

}
