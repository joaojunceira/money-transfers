package com.challenge.domain.repository;

import com.challenge.domain.model.Account;
import java.util.Optional;

public abstract class AccountRepository implements Repository<Long, Account> {

  public abstract Optional<Account> findByIban(String iban);

}
