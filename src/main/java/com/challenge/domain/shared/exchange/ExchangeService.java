package com.challenge.domain.shared.exchange;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

public interface ExchangeService {

  MonetaryAmount exchange(final MonetaryAmount amount, final CurrencyUnit currencyDestination);
}
