package com.challenge.domain.service.transfer;

import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class TransferResult {

  private Long id;

  private String source;

  private String destination;

  private MonetaryAmount amount;
}
