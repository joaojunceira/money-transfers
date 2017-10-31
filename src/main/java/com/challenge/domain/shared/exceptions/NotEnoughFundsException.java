package com.challenge.domain.shared.exceptions;

public final class NotEnoughFundsException extends DomainException {

  public NotEnoughFundsException() {
    super("Not enough funds for transfer");
  }
}
