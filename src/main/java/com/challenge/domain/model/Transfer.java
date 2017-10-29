package com.challenge.domain.model;

import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class Transfer {

  @Setter
  @Getter
  private Long id;
  @Setter
  @Getter
  private Long sourceAccountId;
  @Setter
  @Getter
  private Long destinationAccountId;
  @Setter
  @Getter
  private MonetaryAmount amount;
  @Setter
  @Getter
  private LocalDateTime timestamp;
}
