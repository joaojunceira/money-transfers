package com.challenge.domain.services;

import com.challenge.domain.model.Account;
import java.util.Currency;

public interface AccountService {

  Boolean create(AccountRequestSpecification specification);

  Account get(String iban);
}
