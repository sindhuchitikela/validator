package com.validate.service;

import com.validate.dto.PasswordValidationResponse;
import com.validate.exception.PasswordValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class PasswordValidationServiceImpl implements PasswordValidationService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PasswordValidationServiceImpl.class);

  private final DecryptionService decryptionService;

  @Autowired
  public PasswordValidationServiceImpl(DecryptionService decryptionService) {
    this.decryptionService = decryptionService;
  }

  public PasswordValidationResponse validatePassword(String encryptedPassword) {
    PasswordValidationResponse response = new PasswordValidationResponse();
    StringBuilder errorMessage = new StringBuilder("Invalid password: ");
    try {
      String password = decryptionService.decrypt(encryptedPassword);
      if(password.contains(" ")){
        errorMessage
            .append("Password validation failed - password cannot have spaces ");
        response.setValidPassword(false);
        response.setErrorMessage(errorMessage.toString());
        return response;
      }

      if (password.length() < 5 || password.length() > 12) {
        errorMessage
            .append("Password validation failed - password should be at least 5 characters and at most 12 characters ");
        response.setValidPassword(false);
        response.setErrorMessage(errorMessage.toString());
        return response;
      }

      if (Pattern.compile("[A-Z]").matcher(password).find()) {
        errorMessage.append("Password validation failed - password should NOT contain upper case characters ");
        response.setValidPassword(false);
        response.setErrorMessage(errorMessage.toString());
        return response;
      }

      if (Pattern.compile("(\\w{1,})\\1").matcher(password).find()) {
        errorMessage.append(
            "Password validation failed - password should NOT contain any sequence of characters immediately followed by the same sequence. ");
        response.setValidPassword(false);
        response.setErrorMessage(errorMessage.toString());
        return response;
      }

      if (!Pattern.compile("[a-z]+\\d+|\\d+[a-z]+").matcher(password).find()) {
        errorMessage.append(
            "Password validation failed - password should contain at least one lower case and at least one digit  ");
        response.setValidPassword(false);
        response.setErrorMessage(errorMessage.toString());
        return response;
      }

      if (Pattern.compile("[$&+,:;=?@#|'<>.^*()%!-]").matcher(password).find()) {
        errorMessage.append("Password validation failed - password should NOT contain special characters");
        response.setValidPassword(false);
        response.setErrorMessage(errorMessage.toString());
        return response;
      }
      response.setValidPassword(true);

    } catch (RuntimeException e) {//IllegalArgumentException occurs in case of any illegal base64 character - example: empty input
      response.setErrorMessage(
          errorMessage.toString() + e.getMessage()); //TODO Append a unique ID here for troubleshooting purposes.
      response.setE(new PasswordValidationException(
          "Exception occurred while validating a password. Append transaction ID here for trouble shooting purposes"));
      LOGGER.error(response.getErrorMessage(), response.getE());
    }
    return response;

  }

}

