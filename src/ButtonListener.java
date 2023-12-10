import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonListener implements ActionListener {
    JButton button;

    JTextField textField;

    User user;

    public ButtonListener(JButton button, JTextField textField, User user){
        this.button = button;
        this.textField = textField;
        this.user = user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            user.sendMessage(textField.getText());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        textField.setText("");
    }
}
