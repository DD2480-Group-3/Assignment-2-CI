package se.DD2480Group3.assignment2.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.ResultHandler;


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
     * Calls the lister onBuildFinish methods with the build logs and status
     */
    public void build(OnBuildFinishListener listener){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 

        StringBuilder outputBuilder = new StringBuilder("----------- Starting to build ------------");

        connection.newBuild()
        .setStandardOutput(outputStream)
        .forTasks("build").run(new ResultHandler<Void>() {
            @Override
            public void onFailure(GradleConnectionException failure) {
                outputBuilder
                    .append("\n")
                    .append(outputStream.toString())
                    .append("\n")
                    .append("----------- Build Failed ------------");
                listener.onBuildFinish(outputBuilder.toString(), false);
            }

            @Override
            public void onComplete(Void result) {
                outputBuilder
                    .append("\n")
                    .append(outputStream.toString())
                    .append("\n")
                    .append("----------- Build Completed Tests Are Successful ------------");

                listener.onBuildFinish(outputBuilder.toString(), true);
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

    /**
     * Interface that contains a function to handle the build results
     */
    public interface OnBuildFinishListener {
        
        /**
         * Called when a build is finished
         * @param buildLog The output of the build and tests as a String
         * @param buildSuccesfull True if the build and tests were successful, false otherwise
         */
        public void onBuildFinish(String buildLog, boolean buildSuccesfull);
        
    }
}