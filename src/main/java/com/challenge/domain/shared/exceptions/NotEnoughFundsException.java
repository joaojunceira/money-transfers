package com.challenge.domain.shared.exceptions;

public class NotEnoughFundsException extends DomainException {

  public NotEnoughFundsException() {
    super("Not enough funds for transfer");
  }
}
