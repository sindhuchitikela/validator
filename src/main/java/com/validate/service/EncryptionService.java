package com.validate.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

@Service
public class EncryptionService {

    private static final String           UNICODE_FORMAT = "UTF8";
    public static final  String           DESEDE_ENCRYPTION_SCHEME = "DESede";
    private              KeySpec          ks;
    private              SecretKeyFactory skf;
    private              Cipher           cipher;
    byte[] arrayBytes;
    private String encryptionKey;
    private String encryptionScheme;
    SecretKey key;

    public EncryptionService() throws Exception {
      encryptionKey = "ThisIsUniqueEncryptionKey"; //TODO: This should be read from secrets/hashicorp vault. Can't have secret keys in the code
      encryptionScheme = DESEDE_ENCRYPTION_SCHEME; //TODO: can be read from spring config git
      arrayBytes = encryptionKey.getBytes(UNICODE_FORMAT);
      ks = new DESedeKeySpec(arrayBytes);
      skf = SecretKeyFactory.getInstance(encryptionScheme);
      cipher = Cipher.getInstance(encryptionScheme);
      key = skf.generateSecret(ks);
    }


    public String encrypt(String unencryptedString) {
      String encryptedString = null;
      try {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
        byte[] encryptedText = cipher.doFinal(plainText);
        encryptedString = new String(Base64.encodeBase64(encryptedText));
      } catch (Exception e) {
        e.printStackTrace();
      }
      return encryptedString;
    }
}
