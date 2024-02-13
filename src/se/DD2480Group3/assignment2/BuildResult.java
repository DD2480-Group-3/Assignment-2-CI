package se.DD2480Group3.assignment2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BuildResult {
    
    private ByteArrayOutputStream outputStream;
    private final boolean status;

    public BuildResult(ByteArrayOutputStream outputStream, boolean status){
        this.outputStream = outputStream;
        this.status = status;
    }

    public String asString(){
        return outputStream.toString();
    }

    public boolean asFile(String outputFilePath){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
            outputStream.writeTo(fileOutputStream);
        }catch(Exception e){
            return false;
        }

        return true;
    }

    public OutputStream getOutputStream(){
        return this.outputStream;
    }

    public boolean getStatus(){
        return this.status;
    }

}