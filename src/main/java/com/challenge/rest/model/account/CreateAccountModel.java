package com.challenge.rest.model.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountModel {

  @NotNull
  @Getter
  @Setter
  Long userId;
  @Getter
  @Setter
  @Pattern(message = "Invalid Currency format, it should follow ISO 4217", regexp = "^[A-Z]{2}")
  String currency;
}
