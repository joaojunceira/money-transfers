package com.challenge.rest.model;

import javax.money.MonetaryAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class TransferResultModel {

  @Getter
  @Setter
  Long id;
  @Getter
  @Setter
  MonetaryAmount amount;
  @Getter
  @Setter
  MonetaryAmount newBalance;
}
