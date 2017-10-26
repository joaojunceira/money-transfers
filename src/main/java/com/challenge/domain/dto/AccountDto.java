package com.challenge.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.money.MonetaryAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class AccountDto {

  @Getter
  @Setter
  private String iban;
  @Getter
  @Setter
  private UserDto user;
  @Getter
  @Setter
  private MonetaryAmount balance;
  @Getter
  @Setter
  private LocalDateTime lastMovement;
  @Getter
  @Setter
  private List<TransferDto> movements;
}
