package com.challenge.domain.service.account;


public interface AccountService {

  AccountDetail create(AccountRequest accountRequest);

  AccountDetail get(String iban);
}
