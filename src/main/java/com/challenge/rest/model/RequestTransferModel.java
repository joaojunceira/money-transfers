package com.challenge.rest.model;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class RequestTransferModel {

  @Getter
  @Setter
  @Pattern(message = "Invalid IBAN", regexp = "^[A-Z]{2}\\d{2}(?:\\d{4}){3}\\d{4}(?:\\d\\d?)?$")
  private String source;
  @Getter
  @Setter
  @Pattern(message = "Invalid IBAN", regexp = "^[A-Z]{2}\\d{2}(?:\\d{4}){3}\\d{4}(?:\\d\\d?)?$")
  private String destination;
  @NotNull
  @Getter
  @Setter
  private MonetaryAmount amount;
}
