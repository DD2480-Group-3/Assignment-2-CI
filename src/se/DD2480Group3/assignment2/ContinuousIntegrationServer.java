//for process handling
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.InterruptedException;
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

    if (target.equalsIgnoreCase("/compile")) {
      execute("/home/karl/Documents/GitHub/Assignment-2-CI/scripts/compile.sh");
      /*Send response*/
      response.getWriter().println("Compiling code");
    } else if (target.equalsIgnoreCase("/run")) {
      execute("/home/karl/Documents/GitHub/Assignment-2-CI/scripts/run.sh");
      /*Send response*/
      response.getWriter().println("Starting application");
    } else if (target.equalsIgnoreCase("/test")) {
      execute("/home/karl/Documents/GitHub/Assignment-2-CI/scripts/test.sh");
      /*Send response*/
      response.getWriter().println("Running tests");
    } else {
      response.getWriter().println("Default route, doing nothing");
    }
    // here you do all the continuous integration tasks
    // for example
    // 1st clone your repository
    // 2nd compile the code

  }

  // used to start the CI server in command line
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    server.setHandler(new ContinuousIntegrationServer());
    server.start();
    server.join();
    // System.out.println("main function");
    // EmailService email = new EmailService(
    //   "wenjunjie14@gmail.com",
    //   "wenjunjie14@gmail.com",
    //   "localhost"
    // );
    // System.out.println(email.sendMail("Test Email"));
  }

  /* Source : https://mkyong.com/java/how-to-execute-shell-command-from-java/ */
  private void execute(String filepath) {
    ProcessBuilder compileScript = new ProcessBuilder();
    compileScript.command(filepath);

    try {
      Process process = compileScript.start();

      StringBuilder output = new StringBuilder();

      BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream())
      );

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
      //What is exitval?
      int exitVal = process.waitFor();
      if (exitVal == 0) {
        System.out.println("success!");
        System.out.println(output);
      } else {
        //?
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
