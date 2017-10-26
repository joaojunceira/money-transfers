package com.challenge.domain.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class UserDto {
  @Getter
  @Setter
  private Long id;
  @Getter
  @Setter
  private String firstName;
  @Getter
  @Setter
  private String lastName;
  @Getter
  @Setter
  private LocalDate birthDate;
}
