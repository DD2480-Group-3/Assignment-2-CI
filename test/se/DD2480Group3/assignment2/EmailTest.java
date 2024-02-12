package se.DD2480Group3.assignment2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.junit.jupiter.api.Test;

public class EmailTest {

  @Test
  public void testEmailCrediential() {
    EmailService email = new EmailService("testemail1232456789@gmail.com");
  }
}
