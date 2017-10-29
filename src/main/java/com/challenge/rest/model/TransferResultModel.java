package com.challenge.rest.model;

import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class TransferResultModel {

  @Getter
  @Setter
  Long id;
}
