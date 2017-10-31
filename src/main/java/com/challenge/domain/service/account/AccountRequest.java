package com.challenge.domain.service.account;

import lombok.Data;

public @Data
class AccountRequest {

  private Long userId;

  private String currency;
}
