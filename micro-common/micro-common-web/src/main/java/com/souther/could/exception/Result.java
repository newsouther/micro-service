package com.souther.could.exception;

import java.io.Serializable;
import lombok.Data;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/31 16:08
 */
@Data
public class Result<T> implements Serializable {

  private String code;

  private String msg;

  private T data;

  public static <T> Result<T> success(T data) {
    return result(ResultCode.SUCCESS, data);
  }




  public static <T> Result<T> error() {
    return result(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(),
        ResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), null);
  }

  public static <T> Result<T> error(String msg) {
    return result(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), msg, null);
  }




  public static <T> Result<T> error(String code, String msg) {
    return result(code, msg, null);
  }




  public static <T> Result<T> error(IResultCode resultCode, String msg) {
    return result(resultCode.getCode(), msg, null);
  }

  public static <T> Result<T> error(IResultCode resultCode) {
    return result(resultCode.getCode(), resultCode.getMsg(), null);
  }




  private static <T> Result<T> result(IResultCode resultCode, T data) {
    return result(resultCode.getCode(), resultCode.getMsg(), data);
  }

  private static <T> Result<T> result(String code, String msg, T data) {
    Result<T> result = new Result<T>();
    result.setCode(code);
    result.setData(data);
    result.setMsg(msg);
    return result;
  }
}
