package com.challenge.rest.model.account;


import com.challenge.rest.model.shared.MonetaryAmountModel;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AccountView {

  @NotNull
  @Getter
  @Setter
  String iban;

  @NotNull
  @Getter
  @Setter
  MonetaryAmountModel balance;

  @NotNull
  @Getter
  @Setter
  Long userId;

  @NotNull
  @Getter
  @Setter
  LocalDateTime lastMovement;
}
