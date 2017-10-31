package com.challenge.domain.shared.exceptions;

public class AccountNotFoundException extends DomainException {

  public AccountNotFoundException() {
    super("Account not found");
  }
}
