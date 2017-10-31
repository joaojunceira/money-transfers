package com.challenge.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class Account {

  private Long id;

  private String iban;

  private User user;

  private MonetaryAmount balance;

  private LocalDateTime lastMovement;

  private List<Transfer> movements;
}
