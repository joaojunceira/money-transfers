package com.challenge.domain.repository;

import com.challenge.domain.dto.AccountDto;

public abstract class AccountsRepository implements Repository<String, AccountDto>{

  abstract AccountDto findByIban(String iban);

}
