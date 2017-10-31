package com.challenge.domain.shared.exceptions;

public final class UserNotFoundException extends DomainException {

  public UserNotFoundException() {
    super("User not found");
  }
}
