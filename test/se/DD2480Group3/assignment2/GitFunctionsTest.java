package se.DD2480Group3.assignment2;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.nio.file.Files;

import org.json.JSONObject; 


public class GitFunctionsTest {
    
    /**
     * Tests the cloneRepo function in GitFunctions to see that it returns
     * true when the given the correct repository, username, and token which
     * results in a correct cloning of the repository.
     */
    @Test
    public void cloneTest_0(){

        String repo = "https://github.com/DD2480-Group-3/Assignment-2-CI.git";
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "testRepo";
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
        GitFunctions git = new GitFunctions(repo, filePath, username, token, "main");
        assertTrue(git.cloneRepo());
    }

    /**
     * Tests the cloneRepo function in GitFunctions to make sure that it
     * returns false when the repository, username and token given are incorrect
     * and cloning fails.
     */
    @Test
    public void cloneTest_1(){
        String repo = "https://github.com";
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "testRepo";
        String username = "";
        String token = "";
        
        GitFunctions git = new GitFunctions(repo, filePath, username, token, "main");
        assertFalse(git.cloneRepo());
    }
}
