import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Objects;

public class Window extends JFrame {



    private static JTextArea textArea = null;

    public Window(String address, int port) throws IOException, InterruptedException {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Чат");
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension windowSize = new Dimension(screenSize.width / 2, screenSize.height / 2);
        setSize(windowSize);
        Point windowPosition = new Point(screenSize.width / 4, screenSize.height / 4);
        setLocation(windowPosition);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 2, 1, 1, 4,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(Color.black);
        add(new JScrollPane(textArea), gbc);

        gbc = new GridBagConstraints(
                0, 1, 1, 1, 4, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        JTextField textField = new JTextField("Введите имя");

        FocusListener fl = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Введите имя")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText("Введите имя");
                }
            }
        };
        textField.addFocusListener(fl);
        add(textField, gbc);

        gbc = new GridBagConstraints(
                1, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        JButton btnSend = new JButton("Отправить");
        btnSend.addActionListener(new ButtonListener(btnSend, textField, fl, address, port));
        add(btnSend, gbc);

    }

    public static JTextArea getTextArea() {
        return textArea;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Window wnd = new Window(InetAddress.getLocalHost().getHostAddress(), 12345);
        wnd.setVisible(true);

    }


}