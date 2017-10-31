package com.challenge.domain.model;

import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class Transfer {

  private Long id;

  private Long sourceAccountId;

  private Long destinationAccountId;

  private MonetaryAmount amount;

  private LocalDateTime timestamp;
}
