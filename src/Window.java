import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Window extends JFrame {

    public Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Чат");
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension windowSize = new Dimension(screenSize.width / 2, screenSize.height / 2);
        setSize(windowSize);
        Point windowPosition = new Point(screenSize.width / 4, screenSize.height / 4);
        setLocation(windowPosition);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

    }

    public static void main(String[] args) throws IOException {
        Window wnd = new Window();
        wnd.setVisible(true);
        Socket socket = new Socket(InetAddress.getLocalHost(), 8000);
//        User user = new User("192.168.1.0", 4000);
//        User user = new User("127.0.0.1", 8000);

    }

}