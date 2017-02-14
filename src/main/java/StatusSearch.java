import javax.swing.*;
import java.awt.*;

// For this tutorial, we are trying to follow a data flow similar to
// our React tutorial. Java doesn't support a native notation for callbacks,
// but we can use an interface to hack together an object that allows us
// to mimic passing a function as a parameter. We could just pass a reference
// to the SearchBox when we create it, but this approach is more general.

interface Callback{
    void updateName(String newName);
}
// StatusSearch is composed of a SearchBox and a StatusFeed.
public class StatusSearch extends JPanel implements Callback{

    // Subcomponents
    private SearchBox searchBox;
    private StatusFeed statusFeed;

    // Data
    private String searchName;

    public StatusSearch(JFrame root){
        // Always call the super constructor for Swing components
        super();

        // Setting up the GridBagLayout to help manage components'
        // positions and sizes within the JPanel
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // Add a label for plain text
        this.add(new JLabel("Enter a FaceSpace user's name here:"));

        // Create SearchBox instance, set layout parameter, add to 'this' JPanel
        this.searchBox = new SearchBox(this); // Takes reference to 'this' for callback
        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(this.searchBox, c);

        // Create StatusFeed instance, set layout parameter, add to 'this' JPanel
        this.statusFeed = new StatusFeed(root);
        c.gridy = 2;
        this.add(this.statusFeed, c);
    }

    // The update method that will be called when the button is pressed
    // or enter is keyed after typing. This method will send the updated name
    // to the child StatusFeed so that it can call the API and re-render.
    public void updateName(String newName){
        this.searchName = newName;
        this.statusFeed.updateName(this.searchName);
    }

}
