package utils;

import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProgressEvent;
import org.gradle.tooling.ProgressListener;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.ResultHandler;
import java.io.File;



public class GradleHelper{

    private GradleConnector connector;
    private ProjectConnection connection;

    public GradleHelper(String pathToProject){
        connector = GradleConnector.newConnector();
        connector.forProjectDirectory(fileFromPathName(pathToProject));
        connection = connector.connect();
        
        
    }

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

      
    
    private File fileFromPathName(String pathToProject){
        return new File(pathToProject);
    }

}