package com.validate.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

@Service
public class DecryptionService {
  private static final String           UNICODE_FORMAT = "UTF8";
  public static final  String           DESEDE_ENCRYPTION_SCHEME = "DESede";
  private              KeySpec          ks;
  private              SecretKeyFactory skf;
  private              Cipher           cipher;
  byte[] arrayBytes;
  private String myEncryptionKey;
  private String myEncryptionScheme;
  SecretKey key;

  public DecryptionService() throws Exception {
    myEncryptionKey = "ThisIsUniqueEncryptionKey";
    myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
    arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
    ks = new DESedeKeySpec(arrayBytes);
    skf = SecretKeyFactory.getInstance(myEncryptionScheme);
    cipher = Cipher.getInstance(myEncryptionScheme);
    key = skf.generateSecret(ks);
  }

  public String decrypt(String encryptedString) {
    String decryptedText=null;
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] encryptedText = Base64.decodeBase64(encryptedString);
      byte[] plainText = cipher.doFinal(encryptedText);
      decryptedText= new String(plainText);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return decryptedText;
  }


}
