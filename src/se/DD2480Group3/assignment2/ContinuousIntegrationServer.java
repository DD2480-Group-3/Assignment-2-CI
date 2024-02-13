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
    // EmailService email = new EmailService("testemail1232456789@gmail.com");
    // System.out.println((email.sendMail("IN MAIN")));

    server.setHandler(new ContinuousIntegrationServer());
    server.start();
    server.join();
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

    /*
     * Returns the payload as a json object of a webhook that is sent with
     * Content type: application/json
     * */
    private JSONObject parseWebhook(HttpServletRequest request) throws JSONException, IOException {
        BufferedReader buf = request.getReader();
        StringBuilder builder = new StringBuilder();
        
        String line;
        while((line = buf.readLine()) != null) {
            builder.append(line);
        }
        
        return new JSONObject(builder.toString());
    }

    /*
     *  Returns the name of the branch which triggered the webhook.
     * */
    private String getBranchName(JSONObject payload){
        String ref = payload.getString("ref");
        return ref.replace("refs/heads/","");
    }

}
