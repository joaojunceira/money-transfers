package com.challenge.domain.shared.exchange;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public final class ExchangeServiceDummy implements ExchangeService {

  @Override
  public MonetaryAmount exchange(final MonetaryAmount amount,
      final CurrencyUnit currencyDestination) {
    final MonetaryAmount convertedAmount = Monetary.getDefaultAmountFactory().
        setCurrency(currencyDestination).setNumber(amount.getNumber()).create();
    return convertedAmount;
  }
}
