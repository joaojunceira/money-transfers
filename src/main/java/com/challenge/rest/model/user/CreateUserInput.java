package com.challenge.rest.model.user;


import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class CreateUserInput {

  @NotNull
  @Getter
  @Setter
  String firstName;

  @NotNull
  @Getter
  @Setter
  String lastName;

  @NotNull
  @Getter
  @Setter
  LocalDate birthDate;

}
