package com.challenge.domain.service.exceptions;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException() {
    super("Account not found");
  }
}
