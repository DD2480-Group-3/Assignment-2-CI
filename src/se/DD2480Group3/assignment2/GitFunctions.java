
import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitFunctions {

    private String repo;
    private String filePath;
    private String username;
    private String token;

    public GitFunctions(String repo, String filePath, String username, String token) {
        this.repo = repo;
        this.filePath = filePath;
        this.username = username;
        this.token = token;
    }

    /**
     * Tries to clone Github repository into specific file path and
     * catches errors if unsuccessful.
     * @return boolean indicicating if cloning of repo was successful
     */
    public boolean cloneRepo(){

        File folder = new File(this.filePath);

        if (folder.exists()){
            folder.delete();
        }

        try{
            Git.cloneRepository()
            .setURI(this.repo)
            .setDirectory(new File(this.filePath))
            .setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.username, this.token))
            .call();
            return true;
        }
        catch(Exception e){
            System.err.println("Error cloning repository " + e.getMessage());
        }
        return false;
    }
}
