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

    /*
     * Returns the payload as a json object of a webhook that is sent with
     * Content type: application/json
     * */
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

    /*
     *  Returns the name of the branch which triggered the webhook.
     * */
    public String getBranchName(){
        String ref = this.payload.getString("ref");
        return ref.replace("refs/heads/","");
    }

    /*
     *  Returns the Http Clone url for the repo.
     * */
    public String getRepoHttpUrl() {
        return this.payload.getJSONObject("repository").getString("clone_url");
    }

    /*
     *  Returns the ssh clone url for the repo.
     * */
    public String getRepoSshUrl() {
        return this.payload.getJSONObject("repository").getString("ssh_url");
    }

}
