package com.challenge.domain.dto;

import com.challenge.domain.model.OperationType;
import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class TransferDto {

  @Setter
  @Getter
  private Long id;
  @Setter
  @Getter
  private AccountDto source;
  @Setter
  @Getter
  private AccountDto destination;
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
