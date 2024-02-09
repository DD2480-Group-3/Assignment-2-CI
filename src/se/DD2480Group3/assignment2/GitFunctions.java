
import java.io.File;

import org.eclipse.jgit.api.Git;

public class GitFunctions {

    private String repo;
    private String filePath;

    public GitFunctions(String repo, String filePath) {
        this.repo = repo;
        this.filePath = filePath;
    }

    /**
     * Tries to clon Github repository into specific file path and
     * catches errors if unsuccessful.
     * @return boolean indicicating if cloning of repo was successful
     */
    public boolean cloneRepo(){
        try{
            Git.cloneRepository().setURI(this.repo).setDirectory(new File(this.filePath)).call();
            return true;
        }
        catch(Exception e){
            System.err.println("Error cloning repository" + e.getMessage());
        }
        return false;
    }
}
