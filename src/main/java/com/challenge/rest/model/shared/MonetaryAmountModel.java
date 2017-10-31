package com.challenge.rest.model.shared;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MonetaryAmountModel {

  @Getter
  @Setter
  private BigDecimal amount;
  @Getter
  @Setter
  private String currency;
}
