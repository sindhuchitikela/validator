import com.validate.exception.PasswordValidationException;
import com.validate.service.DecryptionService;
import com.validate.service.PasswordValidationService;
import com.validate.service.PasswordValidationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidationServiceTest {
  private PasswordValidationService passwordValidationService;

  @Mock
  private static DecryptionService decryptionService;


  @Before
  public void init() throws Exception {
    MockitoAnnotations.initMocks(this);

    //Can mock decryption service
    passwordValidationService = new PasswordValidationServiceImpl(decryptionService);
  }

  @Test
  public void validate_password_happy_path() throws PasswordValidationException {
    //Password length = 12
    when(decryptionService.decrypt("pwdvalid1231")).thenReturn("pwdvalid1231");
    assertTrue(passwordValidationService.validatePassword("pwdvalid1231").isValidPassword());

    //Password length = 5
    when(decryptionService.decrypt("val1d")).thenReturn("val1d");
    assertTrue(passwordValidationService.validatePassword("val1d")
        .isValidPassword());

    //Password length = 6
    when(decryptionService.decrypt("valid1")).thenReturn("valid1");
    assertTrue(passwordValidationService.validatePassword("valid1")
        .isValidPassword());
  }

  @Test
  public void validate_password_invalid_characters() throws PasswordValidationException {

    when(decryptionService.decrypt("123456")).thenReturn("123456");
    assertFalse(
        passwordValidationService.validatePassword("123456").isValidPassword());//digits only

    when(decryptionService.decrypt("pwd")).thenReturn("pwd");
    assertFalse(
        passwordValidationService.validatePassword("pwd").isValidPassword());//alphabet  only

    when(decryptionService.decrypt("pwdTEST123")).thenReturn("pwdTEST123");
    assertFalse(passwordValidationService.validatePassword("pwdTEST123")
        .isValidPassword());//upper case

    when(decryptionService.decrypt("pwdTEST1?")).thenReturn("pwdTEST1?");
    assertFalse(passwordValidationService.validatePassword("pwdTEST1?")
        .isValidPassword()); //special chars
  }

  @Test
  public void validate_password_incorrect_length() {
    when(decryptionService.decrypt("password12345")).thenReturn("password12345");
    assertFalse(passwordValidationService.validatePassword("password12345")
        .isValidPassword());//beyond 12 characters

    when(decryptionService.decrypt("pwd")).thenReturn("pwd");
    assertFalse(passwordValidationService.validatePassword("pwd")
        .isValidPassword());//less than 5 characters
  }

  @Test
  public void validate_password_repeated_sequence() {
    when(decryptionService.decrypt("pwdd")).thenReturn("pwdd");
    assertFalse(passwordValidationService.validatePassword("pwdd").isValidPassword());
  }

  @Test
  public void validate_password_with_spaces() {
    when(decryptionService.decrypt("pwdd   12")).thenReturn("pwdd   12");
    assertFalse(passwordValidationService.validatePassword("pwdd   12").isValidPassword());
  }
}
