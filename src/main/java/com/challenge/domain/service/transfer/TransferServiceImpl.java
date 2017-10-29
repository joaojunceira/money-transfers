package com.challenge.domain.service.transfer;

import com.challenge.domain.model.Account;
import com.challenge.domain.model.Transfer;
import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.TransferRepository;
import com.challenge.domain.service.exceptions.AccountNotFoundException;
import com.challenge.domain.service.exceptions.CurrencyNotSupportedException;
import com.challenge.domain.service.exceptions.NotEnoughFundsException;
import com.challenge.domain.shared.exchange.ExchangeService;
import java.time.Clock;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.money.MonetaryAmount;

public class TransferServiceImpl implements TransferService {

  @Inject
  private ExchangeService exchangeService;
  @Inject
  private AccountRepository accountRepository;
  @Inject
  private TransferRepository transferRepository;


  @Override
  public TransferResult doTransfer(TransferRequest transferRequest) {
    if (transferRequest.getAmount().getCurrency() == null) {
      throw new CurrencyNotSupportedException();
    }
    Account source = accountRepository.findByIban(transferRequest.getSource());
    if (source == null) {
      throw new AccountNotFoundException();
    }
    Account destination = accountRepository.findByIban(transferRequest.getDestination());
    if (destination == null) {
      throw new AccountNotFoundException();
    }
    Transfer transfer = createTransfer(source, destination, transferRequest.getAmount());
    debit(source, transfer);
    credit(destination, transfer);
    return new TransferResult(transfer.getId());
  }

  private Transfer createTransfer(Account source, Account destination, MonetaryAmount amount) {
    Transfer transfer = new Transfer();
    transfer.setSourceAccountId(source.getId());
    transfer.setDestinationAccountId(destination.getId());
    transfer.setAmount(amount.negate());
    transfer.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
    Long id = transferRepository.create(transfer);
    transfer.setId(id);
    return transfer;
  }

  private void debit(Account source, Transfer transfer) {
    MonetaryAmount amount = checkIfDifferentCurrencyAndExchange(transfer.getAmount(), source);
    if (!source.getBalance().isGreaterThanOrEqualTo(amount)) {
      throw new NotEnoughFundsException();
    }
    source.getMovements().add(transfer);
    source.setLastMovement(transfer.getTimestamp());
    source.setBalance(source.getBalance().subtract(amount));
    accountRepository.update(source);
  }

  private void credit(Account destination, Transfer transfer) {
    MonetaryAmount amount = checkIfDifferentCurrencyAndExchange(transfer.getAmount(), destination);
    destination.getMovements().add(transfer);
    destination.setLastMovement(transfer.getTimestamp());
    destination.setBalance(destination.getBalance().add(amount));
    accountRepository.update(destination);
  }

  private MonetaryAmount checkIfDifferentCurrencyAndExchange(MonetaryAmount monetaryAmount,
      Account account) {
    if (monetaryAmount.getCurrency().compareTo(account.getBalance().getCurrency()) == 0) {
      return monetaryAmount;
    }
    return exchangeService.exchange(monetaryAmount, account.getBalance().getCurrency());
  }

}
