package com.challenge.domain.service.account;


public interface AccountService {

  AccountDetail create(final AccountRequest accountRequest);

  AccountDetail get(final String iban);
}
