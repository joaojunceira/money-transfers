package com.challenge.domain.service.account;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data
class AccountRequest {

  @Getter
  @Setter
  private Long userId;
  @Getter
  @Setter
  private String currency;
}
