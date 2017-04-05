import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;

/**
 * Jframe that takes care of account creation
 * @author Cameron Nicolle
 */
public class AccountCreation extends JFrame {

    // Subcomponents
    private JTextField textEntry;
    private JButton submitButton;
    private JLabel statusLabel;


    AccountCreation(){
        super("Account Creation");
        this.setLayout(new FlowLayout());
        this.textEntry = new JTextField(15);
        this.submitButton = new JButton("Create Account!");
        this.statusLabel = new JLabel("");

        // Defines button click action to call the callback method
        this.submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textEntry.getText();
                if (AccountCreation.createAccount(text)){
                    statusLabel.setText("Account Created");
                } else {
                    statusLabel.setText("Cannot Create Account");
                }
            }
        });

        // Defines key enter action to call the callback method
        this.textEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textEntry.getText();
                createAccount(text);
            }
        });


        this.setLayout(new GridLayout(16,16));

        // build ui
        this.textEntry.setAlignmentX(3);

        this.add(this.textEntry);

        this.submitButton.setAlignmentX(8);
        this.add(this.submitButton);

        this.statusLabel.setAlignmentX(4);
        this.statusLabel.setAlignmentY(2);
        this.add(this.statusLabel);

        this.setMinimumSize(new Dimension(340, 400));
        this.pack();
        this.setVisible(true);

    }

    /**
     * Creates account and notifies user if successful
     * @param name  name of account
     */
    static public boolean createAccount(String name){
        boolean success = true;
        try{
            URL url = new URL("http://localhost:8080/AccountCreation/createAccount?userName=".concat(name));
            HttpURLConnection api = (HttpURLConnection) url.openConnection();
            api.setRequestMethod("POST");
            api.setRequestProperty( "Content-type", "application/json");
            api.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
            in.close();

        }catch (MalformedURLException e) {
            // new URL() failed
            System.out.println("Bad URL...");
            success = false;
        }
        catch (IOException e) {
            // Catches refusals such as 'not found' or 'unauthorized'
            System.out.println(e.getMessage());
            System.out.println("HTTP Error");
            success = false;
        }

        return success;
    }


}
