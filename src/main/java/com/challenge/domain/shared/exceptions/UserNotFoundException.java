package com.challenge.domain.shared.exceptions;

public class UserNotFoundException extends DomainException {

  public UserNotFoundException() {
    super("User not found");
  }
}
