package com.challenge.domain.shared.iban;

public final class IbanProviderImpl implements IbanProvider {

  @Override
  public String generate(final Long accountId) {
    StringBuilder builder = new StringBuilder("LT60");
    final String idStr = String.valueOf(accountId);
    for (int i = 0; i < (30 - idStr.length()); i++) {
      builder.append("0");
    }
    builder.append(idStr);
    return builder.toString();
  }
}
