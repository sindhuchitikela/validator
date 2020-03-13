package com.validate.controller;

import com.validate.exception.PasswordValidationException;
import com.validate.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncryptionController {

  private EncryptionService encryptionService;

  @Autowired
  public EncryptionController(EncryptionService encryptionService) {
    this.encryptionService = encryptionService;
  }

  /**
   * Added this API just for the demo purposes. Helps the user create an encrypted password
   * @param textToEncrypt
   * @return encryptedString
   */
  @GetMapping("/encrypt_password/{textToEncrypt}")
  public String generateEncryptedPassword(@PathVariable(required = true) String textToEncrypt){
    return encryptionService.encrypt(textToEncrypt);
  }
}