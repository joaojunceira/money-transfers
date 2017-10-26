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
  private Account source;
  @Setter
  @Getter
  private Account destination;
  @Setter
  @Getter
  private MonetaryAmount amount;
  @Setter
  @Getter
  private LocalDateTime timestamp;
  @Setter
  @Getter
  private OperationType type;
}
