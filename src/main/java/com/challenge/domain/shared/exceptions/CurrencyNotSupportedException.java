package com.challenge.domain.shared.exceptions;

public class CurrencyNotSupportedException extends DomainException {

  public CurrencyNotSupportedException() {
    super("Currency not supported");
  }

}
