import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;

public class RecieveS implements Runnable {
    private boolean running;

    private int port;

    User user;

    public RecieveS(boolean running, int port, User user) {
        this.running = running;
        this.port = port;
        this.user = user;
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            while (running) {
                Socket socket = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String word = in.readLine();
                ArrayList<String> users = user.getMediator().getUserIPs();
                if (!users.contains(socket.getLocalAddress().getHostAddress())) {
                    user.getMediator().addUser(new User(word, word, socket.getLocalAddress().getHostAddress(), port, false));
                } else {
                    JTextArea textArea = Window.getTextArea();
                    if (textArea != null && textArea.getText().isEmpty()) {
                        textArea.setText(user.getMediator().getNameByIP(socket.getInetAddress().getHostAddress()) + ": " + word);
                    } else if (textArea != null) {
                        textArea.setText(textArea.getText() + '\n' + user.getMediator().getNameByIP(socket.getInetAddress().getHostAddress()) + ": " + word);
                    }
                }
                System.out.println(word);
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
