package com.dm.fiveE.Result;

import lombok.Data;

@Data
public class Result<T> {
  private Integer code;
  private String msg;
  private T data;

  @Override
  public String toString() {
    return "Result{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
  }
}
