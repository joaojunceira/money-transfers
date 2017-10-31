package com.challenge.rest.model.user;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserView {

  @Getter
  @Setter
  Long id;

  @Getter
  @Setter
  String firstName;

  @Getter
  @Setter
  String lastName;

  @Getter
  @Setter
  LocalDate birthDate;

}
