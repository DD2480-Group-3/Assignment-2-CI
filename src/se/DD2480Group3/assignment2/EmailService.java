import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

  private String to;
  private String from;
  private String host;

  public EmailService(String to, String from, String host) {
    this.to = to;
    this.from = from;
    this.host = host;
  }

  String sendMail() {
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", this.host);
    Session session = Session.getDefaultInstance(properties);

    try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(this.from));

      // Set To: header field of the header.
      message.addRecipient(
        Message.RecipientType.TO,
        new InternetAddress(this.to)
      );

      // Set Subject: header field
      message.setSubject("This is the Subject Line!");

      // Send the actual HTML message, as big as you like
      message.setContent("<h1>This is actual message</h1>", "text/html");

      // Send message
      Transport.send(message);

      return "Sent message successfully....";
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}
