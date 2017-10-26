package com.challenge.domain.service.account;

import java.util.Currency;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class AccountRequestSpecification {

  @Getter
  @Setter
  private Long userId;
  @Getter
  @Setter
  private Currency currency;
}
