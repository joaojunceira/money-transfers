package com.challenge.domain.service.account;


import com.challenge.domain.model.Account;
import com.challenge.domain.model.User;
import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.UserRepository;
import com.challenge.domain.service.exceptions.AccountNotFoundException;
import com.challenge.domain.service.exceptions.CurrencyNotSupportedException;
import com.challenge.domain.service.exceptions.UserNotFoundException;
import com.challenge.domain.shared.IbanUtils;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.LinkedList;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.modelmapper.ModelMapper;

public class AccountServiceImpl implements AccountService {

  @Inject
  private AccountRepository accountRepository;
  @Inject
  private UserRepository userRepository;
  @Inject
  private ModelMapper modelMapper;

  @Override
  public AccountDetail create(AccountRequest accountRequest) {
    User user = userRepository.findById(accountRequest.getUserId());
    if (user == null) {
      throw new UserNotFoundException();
    }
    Account newAccount = new Account();
    newAccount.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    newAccount.setMovements(new LinkedList<>());
    newAccount.setUser(user);
    CurrencyUnit cur = Monetary.getCurrency(accountRequest.getCurrency());
    if (cur == null) {
      throw new CurrencyNotSupportedException();
    }
    MonetaryAmount balance = Monetary.getDefaultAmountFactory().
        setCurrency(cur).setNumber(0).create();
    newAccount.setBalance(balance);
    Long id = accountRepository.create(newAccount);
    newAccount.setIban(IbanUtils.generate(id));
    newAccount.setId(id);
    accountRepository.update(newAccount);
    AccountDetail returnValue = new AccountDetail();
    modelMapper.map(newAccount, returnValue);
    returnValue.setId(id);
    return returnValue;
  }

  @Override
  public AccountDetail get(String iban) {
    AccountDetail accountDetail = new AccountDetail();
    Account account = accountRepository.findByIban(iban);
    if (account == null) {
      throw new AccountNotFoundException();
    }
    modelMapper.map(account, accountDetail);
    return accountDetail;
  }
}
