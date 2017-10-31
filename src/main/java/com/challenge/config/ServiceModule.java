package com.challenge.config;


import com.challenge.domain.service.account.AccountService;
import com.challenge.domain.service.account.AccountServiceImpl;
import com.challenge.domain.service.transfer.TransferService;
import com.challenge.domain.service.transfer.TransferServiceImpl;
import com.challenge.domain.service.user.UserService;
import com.challenge.domain.service.user.UserServiceImpl;
import com.challenge.domain.shared.exchange.ExchangeService;
import com.challenge.domain.shared.exchange.ExchangeServiceDummy;
import com.challenge.domain.shared.iban.IbanProvider;
import com.challenge.domain.shared.iban.IbanProviderImpl;
import com.google.inject.AbstractModule;

public final class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AccountService.class).to(AccountServiceImpl.class);
    bind(ExchangeService.class).to(ExchangeServiceDummy.class);
    bind(TransferService.class).to(TransferServiceImpl.class);
    bind(UserService.class).to(UserServiceImpl.class);
    bind(IbanProvider.class).to(IbanProviderImpl.class);
  }
}
