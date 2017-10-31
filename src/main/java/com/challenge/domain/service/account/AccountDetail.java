package com.challenge.domain.service.account;

import com.challenge.domain.model.User;
import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import lombok.Data;

public @Data
class AccountDetail {

  private Long id;

  private String iban;

  private User user;

  private MonetaryAmount balance;

  private LocalDateTime lastMovement;
}
