package com.challenge.rest.model.transfer;

import com.challenge.rest.model.shared.MonetaryAmountModel;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RequestTransferModel {

  @NotNull
  @Getter
  @Setter
  @Pattern(message = "Invalid IBAN", regexp = "[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}")
  private String source;

  @NotNull
  @Getter
  @Setter
  @Pattern(message = "Invalid IBAN", regexp = "[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}")
  private String destination;

  @NotNull(message = "Amount is empty")
  @Getter
  @Setter
  private MonetaryAmountModel amount;
}
