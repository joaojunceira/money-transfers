package com.challenge.domain.service.account;


public interface AccountService {

  AccountDetail create(AccountRequestSpecification specification);

  AccountDetail get(String iban);
}
