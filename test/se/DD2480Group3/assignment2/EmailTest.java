package se.DD2480Group3.assignment2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.DD2480Group3.assignment2.utils.EmailService;
public class EmailTest {

  /**
   * Tests the send mail function in EmailService.java with a valid mail and text.
   */
  @Test
  @DisplayName("Email Send Test")
  public void testEmailCredential() {
    EmailService email = new EmailService("testemail1232456789@gmail.com");
    boolean status = email.sendMail("Test Mail");
    assertTrue(status);
  }
}
