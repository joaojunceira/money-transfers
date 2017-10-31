package com.challenge.rest.model.shared;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MonetaryAmountModel {

  @NotNull
  @Getter
  @Setter
  private BigDecimal amount;

  @NotNull
  @Getter
  @Setter
  private String currency;
}
