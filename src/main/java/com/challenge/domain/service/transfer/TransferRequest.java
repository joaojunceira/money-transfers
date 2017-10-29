package com.challenge.domain.service.transfer;

import javax.money.MonetaryAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class TransferRequest {

  @Getter
  @Setter
  private String source;
  @Getter
  @Setter
  private String destination;
  @Getter
  @Setter
  private MonetaryAmount amount;
}
