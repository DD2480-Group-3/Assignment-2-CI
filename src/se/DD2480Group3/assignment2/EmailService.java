import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

  private String to;
  private String host;

  public EmailService(String to) {
    this.to = to;
    this.host = "smtp.gmail.com";
  }

  String sendMail(String text) {
    Properties properties = System.getProperties();
    final String user = "testemail1232456789@gmail.com";
    final String pass = "ttni qdwf mdgc rdyq";

    properties.put("mail.smtp.starttls.enable", "true");

    properties.setProperty("mail.smtp.host", this.host);
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.debug", "true");

    Session session = Session.getDefaultInstance(
      properties,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(user, pass);
        }
      }
    );
    String msg = "Fail to send Email";

    try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(user));

      // Set To: header field of the header.
      message.addRecipient(
        Message.RecipientType.TO,
        new InternetAddress(this.to)
      );

      // Set Subject: header field
      message.setSubject("This is the Subject Line!");

      // Send the actual HTML message, as big as you like
      message.setContent("<h1>" + text + "</h1>", "text/html");

      // Send message
      Transport transport = session.getTransport();
      transport.connect();
      Transport.send(message);
      transport.close();

      msg = "Sent message successfully....";
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
    return msg;
  }
}
