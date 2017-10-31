package com.challenge.domain.shared.exceptions;

public final class AccountNotFoundException extends DomainException {

  public AccountNotFoundException() {
    super("Account not found");
  }
}
