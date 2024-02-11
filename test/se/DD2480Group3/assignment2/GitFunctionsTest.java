
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.json.JSONObject;


public class GitFunctionsTest {
    
    @Test
    public void cloneTest_0(){

        String repo = "https://github.com/DD2480-Group-3/Assignment-2-CI.git";
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "CIRepository";
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
        
        GitFunctions git = new GitFunctions(repo, filePath, username, token);
        assertTrue(git.cloneRepo());
    }

    /**
     * Testing the cloneTest function in GitFunctions to make sure that it
     * returns false when the repository, username and token given are incorrect
     * and cloning fails.
     */
    @Test
    public void cloneTest_1(){
        String repo = "https://github.com";
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "CIRepository";
        String username = "";
        String token = "";
        
        GitFunctions git = new GitFunctions(repo, filePath, username, token);
        assertFalse(git.cloneRepo());
    }
}
