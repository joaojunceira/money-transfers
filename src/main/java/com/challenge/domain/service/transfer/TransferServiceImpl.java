package com.challenge.domain.service.transfer;

import com.challenge.domain.model.Account;
import com.challenge.domain.model.Transfer;
import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.TransferRepository;
import com.challenge.domain.shared.exceptions.AccountNotFoundException;
import com.challenge.domain.shared.exceptions.CurrencyNotSupportedException;
import com.challenge.domain.shared.exceptions.NotEnoughFundsException;
import com.challenge.domain.shared.exchange.ExchangeService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;

public final class TransferServiceImpl implements TransferService {

  @Inject
  private ExchangeService exchangeService;
  @Inject
  private AccountRepository accountsRepository;
  @Inject
  private TransferRepository transferRepository;


  @Override
  public TransferResult doTransfer(final TransferRequest transferRequest) {
    CurrencyUnit currency = getCurrency(transferRequest.getCurrency());
    Account source = getAccount(transferRequest.getSource()).get();
    Account destination = getAccount(transferRequest.getDestination()).get();
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency(currency).setNumber(transferRequest.getAmount()).create();
    Transfer transfer = createTransfer(source, destination, amount);
    debit(source, transfer);
    credit(destination, transfer);
    return mapResult(transfer, source, destination);
  }

  private TransferResult mapResult(final Transfer transfer, final Account source,
      final Account destination) {
    TransferResult result = new TransferResult();
    result.setId(transfer.getId());
    result.setAmount(transfer.getAmount());
    result.setSource(source.getIban());
    result.setDestination(destination.getIban());
    return result;
  }

  private Optional<Account> getAccount(final String iban) {
    Optional<Account> source = accountsRepository.findByIban(iban);
    if (!source.isPresent()) {
      throw new AccountNotFoundException();
    }
    return source;
  }

  private CurrencyUnit getCurrency(final String currencyCode) {
    CurrencyUnit currencyUnit;
    try {
      currencyUnit = Monetary.getCurrency(currencyCode);
    } catch (UnknownCurrencyException e) {
      throw new CurrencyNotSupportedException();
    }
    return currencyUnit;
  }

  private Transfer createTransfer(final Account source, final Account destination,
      final MonetaryAmount amount) {
    Transfer transfer = new Transfer();
    transfer.setSourceAccountId(source.getId());
    transfer.setDestinationAccountId(destination.getId());
    transfer.setAmount(amount);
    transfer.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
    Long id = transferRepository.create(transfer);
    transfer.setId(id);
    return transfer;
  }

  private void debit(final Account source, final Transfer transfer) {
    MonetaryAmount amount = checkIfDifferentCurrencyAndExchange(transfer.getAmount(), source);
    if (!source.getBalance().isGreaterThanOrEqualTo(amount)) {
      throw new NotEnoughFundsException();
    }
    source.getMovements().add(transfer);
    source.setLastMovement(transfer.getTimestamp());
    source.getBalance().subtract(amount);
    accountsRepository.update(source);
  }

  private void credit(final Account destination, final Transfer transfer) {
    MonetaryAmount amount = checkIfDifferentCurrencyAndExchange(transfer.getAmount(), destination);
    destination.getMovements().add(transfer);
    destination.setLastMovement(transfer.getTimestamp());
    destination.getBalance().add(amount);
    accountsRepository.update(destination);
  }

  private MonetaryAmount checkIfDifferentCurrencyAndExchange(final MonetaryAmount monetaryAmount,
      final Account account) {
    if (monetaryAmount.getCurrency().equals(account.getBalance().getCurrency())) {
      return monetaryAmount;
    }
    return exchangeService.exchange(monetaryAmount, account.getBalance().getCurrency());
  }

}
