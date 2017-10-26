package com.challenge.domain.dto;

import com.challenge.domain.model.Transfer;
import com.challenge.domain.model.User;
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
  private User user;
  @Getter
  @Setter
  private MonetaryAmount balance;
  @Getter
  @Setter
  private LocalDateTime lastMovement;
  @Getter
  @Setter
  private List<Transfer> movements;
}
