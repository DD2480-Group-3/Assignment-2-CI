public class Main {

  public static void main(String args[]) {
    System.out.println("main function");
    EmailService email = new EmailService(
      "testemail1232456789@gmail.com",
      "testemail1232456789@gmail.com"
    );
    System.out.println(email.sendMail("Test Email"));
  }
}
