package utils;

import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProgressEvent;
import org.gradle.tooling.ProgressListener;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.ResultHandler;
import java.io.File;


/**
 * A utility class for building Gradle Projects.
 */
public class GradleHelper{

    private GradleConnector connector;
    private ProjectConnection connection;


    /**
     * Constructor of GradleHelper class, creates a new connection with the given project.
     * @param pathToProject The path to the project directory to be built. To test for the current project directory "." must be used.
     */
    public GradleHelper(String pathToProject){
        connector = GradleConnector.newConnector();
        connector.forProjectDirectory(fileFromPathName(pathToProject));
        connection = connector.connect();
        
        
    }

    /**
     * Initiates the build process for the Gradle project and prints it's progress.
     * Notifies upon completion, prints to the console if the build and tests are successfully completed or writes the cause of exception in case of a failure.
     */
    public void build(){
        
        connection.newBuild().addProgressListener(new ProgressListener() {
            @Override
            public void statusChanged(ProgressEvent event) {
                System.out.println(event.getDescription());
            }
        }).forTasks("build").run(new ResultHandler<Void>() {
            @Override
            public void onFailure(GradleConnectionException failure) {
                System.out.println("*******BUILD FAILED");
                System.out.println("Cause : "+failure.getCause().getMessage());
            }

            @Override
            public void onComplete(Void result) {
                System.out.println("-----------Build Completed Tests Are Successful------------");
 
            }
        });

        connection.close();
    }

      
    /**
     * Creates a new File from the given path.
     * @param pathToProject The path to the project directory. 
     * @return A File object representing the project directory.
     */
    private File fileFromPathName(String pathToProject){
        return new File(pathToProject);
    }

}