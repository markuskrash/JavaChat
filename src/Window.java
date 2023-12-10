import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Window extends JFrame {

    private User user;

    private static JTextArea textArea = null;

    public Window() throws IOException, InterruptedException {
        Mediator mediator = new Mediator(8080);
        User user = new User(InetAddress.getLocalHost().getHostAddress(), 8080, true);
        mediator.addUser(user);
        user.setMediator(mediator);
        Thread thread = new Thread(user);
        thread.start();

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

        JTextField textField = new JTextField();
        add(textField, gbc);

        gbc = new GridBagConstraints(
                1, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        JButton btnSend = new JButton("Отправить");
        btnSend.addActionListener(new ButtonListener(btnSend, textField, user));
        add(btnSend, gbc);

    }

    public static JTextArea getTextArea(){
        return textArea;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Window wnd = new Window();
        wnd.setVisible(true);
//        User user = new User("192.168.1.0", 4000);

    }

}