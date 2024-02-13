package se.DD2480Group3.assignment2; 

//for process handling
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import se.DD2480Group3.assignment2.utils.GradleHelper;

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
        
        //Cloning
        String repo = handler.getRepoHttpUrl();
        String filePath = "repos/" + handler.getRepoName();

        String secretPath = "Secret.json";
        String username = "";
        String token = "";

        SecretManager secret = new SecretManager(secretPath);

        try{
            JSONObject json = secret.readCredentials();
            username = json.getString("github_username");
            token = json.getString("github_token");
        }catch (Exception e){

        }


        GitFunctions git = new GitFunctions(repo, filePath, username, token, handler.getBranchName());
        git.cloneRepo();  

        handler.createGradleSettings();
        //end of cloning
  
        //Building
            
        GradleHelper helper = new GradleHelper(filePath);
        
        EmailService email = new EmailService("karlspetsblomberg@gmail.com");


        GradleHelper.OnBuildFinishListener listener = new GradleHelper.OnBuildFinishListener(){
            @Override
            public void onBuildFinish(String output, boolean status){
                email.sendMail(output);
            }

        };

        helper.build(listener);

        //End of Building
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

}
