package com.validate.service;
import com.validate.dto.PasswordValidationResponse;

public interface PasswordValidationService {
  PasswordValidationResponse validatePassword(String pwd);
 }
