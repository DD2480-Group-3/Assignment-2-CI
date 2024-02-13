package se.DD2480Group3.assignment2;

import org.json.JSONObject;
import java.io.BufferedReader;  
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

class WebhookHandler {
    
    HttpServletRequest request;
    JSONObject payload;

    public WebhookHandler(HttpServletRequest request){
        this.request = request;
        this.payload = createPayload();
    }

    private JSONObject createPayload(){
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader buf = request.getReader();
            String line;
            while((line = buf.readLine()) != null) {
                builder.append(line);
            }
        }catch(IOException e){
            System.err.println("Error reading request" + e.getMessage());
        }
        return new JSONObject(builder.toString());
    }
}
