package com.challenge.domain.service.exceptions;

public class CurrencyNotSupportedException extends RuntimeException {

  public CurrencyNotSupportedException() {
    super("Currency not supported");
  }

}
