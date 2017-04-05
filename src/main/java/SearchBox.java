import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A SearchBox is composed of a JTextField and a JButton
// ActionListeners will call the callback method when user
// presses enter or clicks button. This will send the new name
// back up to the parent StatusSearch.
public class SearchBox extends JPanel {

    // Subcomponents
    private JTextField textEntry;
    private JButton submitButton;

    public SearchBox(final Callback callback){
        // Initialize this component
        super();
        this.setLayout(new FlowLayout());
        this.textEntry = new JTextField(15);
        this.submitButton = new JButton("Get Statuses!");

        // Defines button click action to call the callback method
        this.submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textEntry.getText();
                callback.updateName(text);
            }
        });

        // Defines key enter action to call the callback method
        this.textEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textEntry.getText();
                callback.updateName(text);
            }
        });

        // Add subcomponents to this JPanel
        this.add(this.textEntry);
        this.add(this.submitButton);
    }
}
