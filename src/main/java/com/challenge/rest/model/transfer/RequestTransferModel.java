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

  @Getter
  @Setter
  @Pattern(message = "Invalid IBAN", regexp = "^[A-Z]{2}\\d{2}(?:\\d{4}){3}\\d{4}(?:\\d\\d?)?$")
  private String source;

  @Getter
  @Setter
  @Pattern(message = "Invalid IBAN", regexp = "^[A-Z]{2}\\d{2}(?:\\d{4}){3}\\d{4}(?:\\d\\d?)?$")
  private String destination;

  @NotNull(message = "Amount is empty")
  @Getter
  @Setter
  private MonetaryAmountModel amount;
}
