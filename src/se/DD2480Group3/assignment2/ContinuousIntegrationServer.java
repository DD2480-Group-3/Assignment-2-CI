package se.DD2480Group3.assignment2; 

//for process handling
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

// import src.se.DD2480Group3.assignment2.EmailService;

/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler {

  public void handle(
    String target,
    Request baseRequest,
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException, ServletException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    baseRequest.setHandled(true);

    System.out.println("Given route: " + target);

    WebhookHandler handler = new WebhookHandler(request);
    
        System.out.println(handler.getBranchName());
        System.out.println(handler.getRepoSshUrl());
        System.out.println(handler.getRepoHttpUrl());
  }

  // used to start the CI server in command line
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    // EmailService email = new EmailService("testemail1232456789@gmail.com");
    // System.out.println((email.sendMail("IN MAIN")));

    server.setHandler(new ContinuousIntegrationServer());
    server.start();
    server.join();
  }

}
