import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

  private String to;
  private String host;
  private static final String USER = "testemail1232456789@gmail.com";
  private static final String PASS = "ttni qdwf mdgc rdyq";
  private Session sesh;

  public EmailService(String to) {
    this.to = to;
    this.host = "smtp.gmail.com";

    Properties properties = System.getProperties();

    properties.put("mail.smtp.starttls.enable", "true");

    properties.setProperty("mail.smtp.host", this.host);
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.debug", "true");

    this.sesh =
      Session.getDefaultInstance(
        properties,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
              EmailService.USER,
              EmailService.PASS
            );
          }
        }
      );
  }

  String sendMail(String text) {
    String status = "Fail to send Email";

    try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(this.sesh);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(EmailService.USER));

      // Set To: header field of the header.
      message.addRecipient(
        Message.RecipientType.TO,
        new InternetAddress(this.to)
      );

      // Set Subject: header field
      message.setSubject("Sending reminder");

      // Send the actual HTML message, as big as you like
      message.setContent("<h1>" + text + "</h1>", "text/html");

      // Send message
      Transport transport = this.sesh.getTransport();
      transport.connect();
      Transport.send(message);
      transport.close();

      status = "Sent message successfully....";
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
    return status;
  }
}