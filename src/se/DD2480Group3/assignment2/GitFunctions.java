package se.DD2480Group3.assignment2;
 
import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FileUtils;

public class GitFunctions {
 
    private String repo;
    private String filePath;
    private String username;
    private String token;
    private String branch;

    /**
     * Initializes a new instance of GitFunctions
     * @param repo      String containing the HTTPS link to the repo to be cloned
     * @param filePath  String defining the path to locate the repo locally
     * @param username  String containing a github username
     * @param token     String containing a github token
     */
    public GitFunctions(String repo, String filePath, String username, String token) {
        this.repo = repo;
        this.filePath = filePath;
        this.username = username;
        this.token = token;
    }

    public GitFunctions(String repo, String filePath, String username, String token, String branch) {
        this.repo = repo;
        this.filePath = filePath;
        this.username = username;
        this.token = token;
        this.branch = branch;
    }

    /**
     * Tries to clone Github repository into specific file path and
     * catches errors if unsuccessful.
     * @return boolean indicicating if cloning of repo was successful
     */
    public boolean cloneRepo(){

        File folder = new File(this.filePath);
        
        if (folder.exists()){
            deleteDirectory(folder);
        }

        try{
            Git.cloneRepository()
            .setURI(this.repo)
            .setDirectory(new File(this.filePath))
            .setBranch(this.branch)
            .setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.username, this.token))
            .call();
            return true;
        }
        catch(Exception e){
            System.err.println("Error cloning repository " + e.getMessage());
        }
        return false;
    }

    /**
     * Delete a folder/file and all of the folders/files that are inside it
     * @param file  File to delete
     */
    public static void deleteDirectory(File file)
    {
        for (File subfile : file.listFiles()) {

            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }
}
