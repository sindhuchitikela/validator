package com.validate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.validate.exception.PasswordValidationException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordValidationResponse {

  private boolean                     isValidPassword;
  private String                      errorMessage;
  @JsonIgnore
  private PasswordValidationException e;

}
