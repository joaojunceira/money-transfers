package com.challenge.domain.service.user;

import java.time.LocalDate;
import lombok.Data;

public @Data
class UserDetail {

  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
}
