package com.challenge.domain.service.exceptions;

public class NotEnoughFundsException extends RuntimeException {

  public NotEnoughFundsException() {
    super("Not enough funds for transfer");
  }
}
