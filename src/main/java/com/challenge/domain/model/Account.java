package com.challenge.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class Account {

  @Getter
  @Setter
  private Long id;
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
  @NonNull
  @Getter
  @Setter
  private List<Transfer> movements;
}
