package com.challenge.domain.shared;

public class IbanUtils {

  //Iban generator Helper
  public static String generate(Long id) {
    StringBuilder builder = new StringBuilder("LT60");
    String idStr = String.valueOf(id);
    for (int i = 0; i < (30 - idStr.length()); i++) {
      builder.append("0");
    }
    builder.append(idStr);
    return builder.toString();
  }

}
