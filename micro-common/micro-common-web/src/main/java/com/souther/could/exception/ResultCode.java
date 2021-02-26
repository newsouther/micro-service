package com.souther.could.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

  SUCCESS("200", "一切ok"),

  FRONT_ERROR("400", "未知错误"),
  FRONT_NOT_EXIST("401", "通用：不存在"),
  FRONT_PARAM("403", "自定义输出-参数、请求错误"),
  FRONT_CREDENTIAL("404", "凭证错误"),


  SYSTEM_EXECUTION_ERROR("500", "系统执行出错"),

  ;

  private String code;

  private String msg;

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMsg() {
    return msg;
  }
}
