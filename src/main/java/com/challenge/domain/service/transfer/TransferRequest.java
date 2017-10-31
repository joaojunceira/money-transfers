package com.challenge.domain.service.transfer;

import java.math.BigDecimal;
import lombok.Data;

public @Data
class TransferRequest {

  private String source;

  private String destination;

  private BigDecimal amount;

  private String currency;
}
