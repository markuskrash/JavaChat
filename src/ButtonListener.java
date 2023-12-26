import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.Objects;

public class ButtonListener implements ActionListener {
    JButton button;

    JTextField textField;

    User user;

    Mediator mediator;

    FocusListener fl;
    String address;
    int port;

    public ButtonListener(JButton button, JTextField textField, FocusListener fl, String address, int port){
        this.button = button;
        this.textField = textField;
        this.fl = fl;
        this.address = address;
        this.port = port;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(fl, textField.getFocusListeners()[textField.getFocusListeners().length - 1])){
            mediator = new Mediator(port);
            try {
                user = new User("Вы", textField.getText(), address, port, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            mediator.addUser(user);
            user.setMediator(mediator);
            Thread thread = new Thread(user);
            thread.start();
            textField.removeFocusListener(fl);
            textField.setText("");
        }else {
            try {
                user.sendMessage(textField.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            textField.setText("");
        }

    }
}
