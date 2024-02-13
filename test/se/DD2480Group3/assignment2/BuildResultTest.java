package se.DD2480Group3.assignment2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BuildResultTest {
    
    /**
     * Tests the BuildResult class to put the build result message as a string.
     */
    @Test
    @DisplayName("Build Result Test as String")
    public void testAsStringResults(){
        String str = "This is a test String";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(str.getBytes());
        BuildResult result = new BuildResult(byteArrayOutputStream, false);
        assertEquals(result.asString(), str);
    }

    /**
     * Tests the BuildResult class to put the build result message in a file.
     */
    @Test
    @DisplayName("Build Result Test as File")
    public void testAsFile(){
        String str = "This is a test String to put in a file";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(str.getBytes());
        BuildResult result = new BuildResult(byteArrayOutputStream, false);

        assertTrue(result.asFile("resultTestAsFile.log"));
        try{
            FileInputStream inputStream = new FileInputStream("resultTestAsFile.log");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(inputStream.readAllBytes());
            inputStream.close();
            assertEquals(outputStream.toString(), str);
        }catch(Exception e){
            throw new Error(e);
        }

        File file = new File("resultTestAsFile.log");
        file.delete(); 
    } 
}
