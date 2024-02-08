public class Main {

  public static void main(String args[]) {
    System.out.println("main function");
    EmailService email = new EmailService(
      "wenjunjie14@gmail.com",
      "wenjunjie14@gmail.com",
      "localhost"
    );
    System.out.println(email.sendMail());
  }
}
