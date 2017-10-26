package com.challenge.domain.service.account;

import com.challenge.domain.model.User;
import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class AccountDetail {

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
}
