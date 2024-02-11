import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class SecretManager {

    private String filePath;

    public SecretManager(String filePath){
        this.filePath = filePath;
    }

    public JSONObject readCredentials() throws Exception{
        String content = new String(Files.readAllBytes(Paths.get(this.filePath)));

        JSONObject json = new JSONObject(content);

        return json;
    }
}
