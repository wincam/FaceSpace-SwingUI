import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import com.google.gson.*;

// StatusFeed is implemented as an extension of JTextArea.
// Instead of using a separate Status class, individual statuses
// are represented using Strings.
public class StatusFeed extends JTextArea {

    // The JSON API we are using (GSON) requires model classes for any
    // object type we expect to receive. This way, GSON can return an
    // easily usable Java class instance after a successful REST API call.
    //
    // IMPORTANT: Instance variable names much match EXACTLY with the JSON
    // object names/keys.
    //
    // We can use a private class here because this type will not be used elsewhere.
    private class StatusJSON{
        String statusText;
        Date dateCreated;
        Date dateLastEdited;
        public String toString(){
            return statusText;
        }
    }

    // A default text value to help with rendering
    static final String defaultText = "\n\n\n\n\n\n\n\n\n\n";

    // A reference to the root JFrame (i.e. AppContainer)
    // used only for calling its 'pack' method.
    JFrame root;

    // Basic initialization
    public StatusFeed(JFrame root){
        super();
        this.setLayout(new GridLayout(0,1));
        this.root = root;
        this.setText(defaultText);
    }

    // Due to the way we've set up our ActionListeners and Callback method,
    // this method will be called whenever the user enters and submits
    // a new name. This method will clear the old text, call the API,
    // and display the new statuses (if there is anything to show).
    public void updateName(String username){
        this.setText(defaultText);
        this.setText(callAPI(username));
        this.root.pack();
        this.setVisible(false);
        this.setVisible(true);
    }

    // This method calls the REST API and requests a given user's status updates.
    // We are using BLOCKING or SYNCHRONOUS communication in this method. As a result,
    // the current thread will not resume execution until the API has responded or timed out.
    // Without threading, this should be considered a low-quality solution.
    //
    // This method also uses the GSON for JSON parsing (GSON because it's by Google).
     public String callAPI(String name){
        try {
            // Initialize GSON
            Gson gson = new GsonBuilder().create();
            // Initialize result String
            String fromAPI = "";
            // Call the API
            URL myURL = new URL("http://localhost:8080/profileDisplay/getUserPosts?userName=" + name);
            URLConnection api = myURL.openConnection();
            // Call the API (ASYNCHRONOUS)
            api.connect();
            // Set up input stream reader
            BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
            String inputLine;
            // Read from input steam
            while ((inputLine = in.readLine()) != null){
                // Declare the expected type of JSON results - in this case an array of Status objects
                StatusJSON[] statusArray = gson.fromJson(inputLine, StatusJSON[].class);
                // For each status found in JSON, append name, date, and status to result String
                for(StatusJSON sj : statusArray){
                    fromAPI += String.format("%s - %s\n%s\n\n", name, sj.dateCreated, sj.statusText);
                }
            }
            // Close the stream and return the result
            in.close();
            return fromAPI;
        }
        catch (MalformedURLException e) {
            // new URL() failed
            System.out.println("Bad URL...");
        }
        catch (IOException e) {
            // Catches refusals such as 'not found' or 'unauthorized'
            System.out.println("HTTP Error");
        }
        // Default return value
        return "";
    }
}
