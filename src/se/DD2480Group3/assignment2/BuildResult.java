package se.DD2480Group3.assignment2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BuildResult {
    
    private ByteArrayOutputStream outputStream;
    private final boolean status;

    /**
     * Constructor for BuildResult class
     * @param outputStream the output stream for the result to be written
     * @param status boolean variable which is true if build is succesful and false otherwise
     */
    public BuildResult(ByteArrayOutputStream outputStream, boolean status){
        this.outputStream = outputStream;
        this.status = status;
    }

    /**
     * Function to convert the result message to a string
     * @return the result as a string
     */
    public String asString(){
        return outputStream.toString();
    }

    /**
     * Function to write the output of the build to a file
     * @param outputFilePath the path of the file to be written
     * @return true if message is successfully written to the file, false otherwise.
     */
    public boolean asFile(String outputFilePath){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
            outputStream.writeTo(fileOutputStream);
        }catch(Exception e){
            return false;
        }

        return true;
    }

    /**
     * Getter for outputStream attribute
     * @return output stream of the class
     */
    public OutputStream getOutputStream(){
        return this.outputStream;
    }

    /**
     * Getter for status attribute
     * @return status of the build
     */
    public boolean getStatus(){
        return this.status;
    }

}