package com.challenge.config;

import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.TransferRepository;
import com.challenge.domain.repository.UserRepository;
import com.challenge.infrastructure.persistence.AccountsRepositoryInMemory;
import com.challenge.infrastructure.persistence.TransferRepositoryInMemory;
import com.challenge.infrastructure.persistence.UserRepositoryInMemory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.modelmapper.ModelMapper;


public final class RepositoryModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AccountRepository.class).to(AccountsRepositoryInMemory.class).in(Singleton.class);
    bind(TransferRepository.class).to(TransferRepositoryInMemory.class).in(Singleton.class);
    bind(UserRepository.class).to(UserRepositoryInMemory.class).in(Singleton.class);
  }

  @Provides
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
