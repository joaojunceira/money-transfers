package com.challenge.domain.shared.iban;

public interface IbanProvider {

  /*
   * Creation of IBAN based on Account Id
   */
  String generate(final Long accountId);
}
