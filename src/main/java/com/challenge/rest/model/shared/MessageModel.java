package com.challenge.rest.model.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {

  @Getter
  @Setter
  private String message;
}
