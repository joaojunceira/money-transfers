package com.challenge.domain.repository;

import com.challenge.domain.model.Account;

public abstract class AccountRepository implements Repository<Long, Account> {

  public abstract Account findByIban(String iban);

}
