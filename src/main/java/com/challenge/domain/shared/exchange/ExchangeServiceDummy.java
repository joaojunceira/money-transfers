package com.challenge.domain.shared.exchange;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class ExchangeServiceDummy implements ExchangeService {

  @Override
  public MonetaryAmount exchange(MonetaryAmount amount, CurrencyUnit currencyDestination) {
    MonetaryAmount convertedAmount = Monetary.getDefaultAmountFactory().
        setCurrency(currencyDestination).setNumber(amount.getNumber()).create();
    return convertedAmount;
  }
}
