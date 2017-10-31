package com.challenge.rest.model.shared;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {

  @NotNull
  @Getter
  @Setter
  private String message;
}
