package Components;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Class controlling all Usability (ease of use) methods used throughout the
 * application.
 *
 * @author 23364602 - Joshua Middleton
 */
public class Usability {

    private static JTextField textField;
    private static String textFieldDefaultText;

    /**
     * Method checks if the default text set through the GUI is the same as the
     * current text value of a JTextField, if so it removes the placeholder text
     * and sets the text to nothing.
     *
     * @param textField - Targeted JTextField.
     * @param defaultText - Desired text to set JTextField to.
     */
    public static void emptyTextField(JTextField textField, String defaultText) {
        if (textField.getText().equalsIgnoreCase(defaultText)) {
            textField.setText("");
        }
    }

    /**
     * Method checks if the value of the JTextField is nothing, if so it will
     * set the text to the second argument involved when calling the method.
     *
     * @param textField - Targeted JTextField.
     * @param placeholderText - Desired text to set JTextField to.
     */
    public static void setPlaceholderText(JTextField textField, String placeholderText) {
        if ("".equalsIgnoreCase(textField.getText())) {
            textField.setText(placeholderText);
        }
    }

        /**
     * Method sending a option pane box displaying a message along with a title.
     * The box has an okay button on default.
     *
     * The initial parameter which holds null value centres the box in the
     * middle of the screen.
     *
     * @param message - Message the Notification Box holds.
     * @param title - Title of the Notification Box.
     */
    public static void sendOptionPane(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
}
