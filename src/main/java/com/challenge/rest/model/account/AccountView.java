package com.challenge.rest.model.account;


import com.challenge.rest.model.shared.MonetaryAmountModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AccountView {

  @Getter
  @Setter
  String iban;
  @Getter
  @Setter
  MonetaryAmountModel balance;
  @Getter
  @Setter
  Long userId;
  @Getter
  @Setter
  LocalDateTime lastMovement;
}
