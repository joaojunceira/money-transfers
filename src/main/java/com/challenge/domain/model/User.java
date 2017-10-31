package com.challenge.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class User {

  private Long id;

  private String firstName;

  private String lastName;

  private LocalDate birthDate;
}
