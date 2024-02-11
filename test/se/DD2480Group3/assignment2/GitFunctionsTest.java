
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.json.JSONObject;


public class GitFunctionsTest {
    
    @Test
    public void cloneTest(){

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
}
