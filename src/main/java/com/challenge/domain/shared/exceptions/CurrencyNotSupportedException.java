package com.challenge.domain.shared.exceptions;

public final class CurrencyNotSupportedException extends DomainException {

  public CurrencyNotSupportedException() {
    super("Currency not supported");
  }

}
