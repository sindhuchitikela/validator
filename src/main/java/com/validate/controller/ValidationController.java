package com.validate.controller;

import com.validate.dto.PasswordDto;
import com.validate.dto.PasswordValidationResponse;
import com.validate.service.PasswordValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/validate")
public class ValidationController {

  private PasswordValidationService passwordValidationService;

  @Autowired
  public ValidationController(PasswordValidationService passwordValidationService) {
    this.passwordValidationService = passwordValidationService;
  }

  @GetMapping("/password")
  public PasswordValidationResponse validatePassword(@RequestBody(required = true)PasswordDto passwordDto)   {
    return passwordValidationService.validatePassword(passwordDto.getPassword());
  }

}