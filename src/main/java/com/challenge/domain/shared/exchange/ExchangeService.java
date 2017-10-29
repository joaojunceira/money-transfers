package com.challenge.domain.shared.exchange;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

public interface ExchangeService {

  MonetaryAmount exchange(MonetaryAmount amount, CurrencyUnit currencyDestination);
}
