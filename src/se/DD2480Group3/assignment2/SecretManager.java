import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class SecretManager {

    private String filePath;

    /**
     * Initializes a new instance of SecretManager
     * @param filePath  String that contains information about the location of the secret file
     */
    public SecretManager(String filePath){
        this.filePath = filePath;
    }

    /**
     * Function to read content of the file Secret.json
     * @return  returns a json object
     * @throws Exception throws exception if the function fails to find the json file
     */
    public JSONObject readCredentials() throws Exception{
        String content = new String(Files.readAllBytes(Paths.get(this.filePath)));

        JSONObject json = new JSONObject(content);

        return json;
    }
}
