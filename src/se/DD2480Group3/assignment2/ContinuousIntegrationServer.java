package se.DD2480Group3.assignment2; 

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
import org.json.JSONObject;
import org.json.JSONException;

//import src.se.DD2480Group3.assignment2.EmailService;

/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler {

  
  /**
     * Handles HTTP requests.
     * 
     * @param target The target of the request.
     * @param baseRequest The base HTTP request.
     * @param request The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @throws IOException If an I/O error occurs.
     * @throws ServletException If a servlet exception occurs.
     */
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

  /**
   * Main function that is used to start the CI server in command line
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    // EmailService email = new EmailService("testemail1232456789@gmail.com");
    // System.out.println((email.sendMail("IN MAIN")));

    server.setHandler(new ContinuousIntegrationServer());
    server.start();
    server.join();
  }

  /**
   * Function to execute a shell script.
   * Source : https://mkyong.com/java/how-to-execute-shell-command-from-java/
   * @param filepath The path to the shell script.
   */
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
