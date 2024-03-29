package se.DD2480Group3.assignment2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;
import se.DD2480Group3.assignment2.utils.EmailService;
import se.DD2480Group3.assignment2.utils.GradleHelper;

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

    //Cloning
    String repo = handler.getRepoHttpUrl();
    String filePath = "repos/" + handler.getRepoName();

    String secretPath = "Secret.json";
    String username = "";
    String token = "";

    SecretManager secret = new SecretManager(secretPath);

    try {
      JSONObject json = secret.readCredentials();
      username = json.getString("github_username");
      token = json.getString("github_token");
    } catch (Exception e) {
      System.out.println(e);
    }

    GitFunctions git = new GitFunctions(
      repo,
      filePath,
      username,
      token,
      handler.getBranchName()
    );
    git.cloneRepo();

    handler.createGradleSettings();
    //end of cloning

    //Building

    GradleHelper helper = new GradleHelper(filePath);

    EmailService email = new EmailService(username);
    GradleHelper.OnBuildFinishListener listener = new GradleHelper.OnBuildFinishListener() {
        @Override
        public void onBuildFinish(BuildResult result) {
            email.sendMail(result.asString()); 
            String time = LocalDateTime.now().toString().replaceAll(":", "-").substring(0, 19);
            result.asFile("logs/Build-Log-".concat(time).concat(".log"));  
        }
    };


    helper.build(listener);

    //End of Building
    response.getWriter().println("OK");
  }

  /**
   * Main function that is used to start the CI server in command line
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    server.setHandler(new ContinuousIntegrationServer());
    server.start();
    server.join();
  }
}
