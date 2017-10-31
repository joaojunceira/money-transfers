package com.challenge.domain.service.account;


import com.challenge.domain.model.Account;
import com.challenge.domain.model.User;
import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.UserRepository;
import com.challenge.domain.shared.exceptions.AccountNotFoundException;
import com.challenge.domain.shared.exceptions.CurrencyNotSupportedException;
import com.challenge.domain.shared.exceptions.UserNotFoundException;
import com.challenge.domain.shared.iban.IbanProvider;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import org.modelmapper.ModelMapper;

public final class AccountServiceImpl implements AccountService {

  @Inject
  private AccountRepository accountsRepository;
  @Inject
  private UserRepository userRepository;
  @Inject
  private IbanProvider ibanProvider;
  @Inject
  private ModelMapper modelMapper;

  @Override
  public AccountDetail create(final AccountRequest accountRequest) {
    CurrencyUnit cur = getCurrency(accountRequest);
    Optional<User> user = getUser(accountRequest);
    Account newAccount = createEntityAccount(cur, user);
    Long id = accountsRepository.create(newAccount);
    newAccount.setIban(ibanProvider.generate(id));
    newAccount.setId(id);
    accountsRepository.update(newAccount);
    AccountDetail returnValue = new AccountDetail();
    modelMapper.map(newAccount, returnValue);
    returnValue.setId(id);
    return returnValue;
  }

  private Account createEntityAccount(CurrencyUnit cur, Optional<User> user) {
    Account newAccount = new Account();
    newAccount.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    newAccount.setMovements(new LinkedList<>());
    newAccount.setUser(user.get());
    MonetaryAmount balance = Monetary.getDefaultAmountFactory().
        setCurrency(cur).setNumber(0).create();
    newAccount.setBalance(balance);
    return newAccount;
  }

  private Optional<User> getUser(AccountRequest accountRequest) {
    Optional<User> user = userRepository.findById(accountRequest.getUserId());
    if (!user.isPresent()) {
      throw new UserNotFoundException();
    }
    return user;
  }

  private CurrencyUnit getCurrency(AccountRequest accountRequest) {
    CurrencyUnit cur;
    try {
      cur = Monetary.getCurrency(accountRequest.getCurrency());
    } catch (UnknownCurrencyException e) {
      throw new CurrencyNotSupportedException();
    }
    return cur;
  }

  @Override
  public AccountDetail get(final String iban) {
    AccountDetail accountDetail = new AccountDetail();
    Optional<Account> account = accountsRepository.findByIban(iban);
    if (!account.isPresent()) {
      throw new AccountNotFoundException();
    }
    modelMapper.map(account.get(), accountDetail);
    return accountDetail;
  }
}
