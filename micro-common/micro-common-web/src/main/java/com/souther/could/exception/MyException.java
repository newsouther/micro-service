package com.souther.could.exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {

  private String code;

  public MyException(String code, String message) {
    super(message);
    this.code = code;
  }

}
