package com.challenge.domain.model;

public enum OperationType {
  UNKNOWN(0),
  DEBIT(1),
  CREDIT(2);
  private final Integer code;

  OperationType(Integer code) {
    this.code = code;
  }

  public Integer code(){
    return code;
  }
}
