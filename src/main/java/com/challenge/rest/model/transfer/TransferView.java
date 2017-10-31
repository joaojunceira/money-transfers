package com.challenge.rest.model.transfer;

import com.challenge.rest.model.shared.MonetaryAmountModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class TransferView {

  @Getter
  @Setter
  Long id;
  @Getter
  @Setter
  String source;
  @Getter
  @Setter
  String destination;
  @Getter
  @Setter
  MonetaryAmountModel amount;
}
