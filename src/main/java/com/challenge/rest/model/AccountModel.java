package com.challenge.rest.model;

import javax.money.MonetaryAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class AccountModel {

  @Getter
  @Setter
  String iban;
  @Getter
  @Setter
  MonetaryAmount balance;
}
