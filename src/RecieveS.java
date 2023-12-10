import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
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
                ArrayList<String> users= user.getMediator().getUsers();
                if(!users.contains(word) && word.contains(".")){
                    user.getMediator().addUser(new User(word, port, false));

                }else if (!word.contains(".")){
                    JTextArea  textArea = Window.getTextArea();
                    if(textArea != null && textArea.getText().isEmpty()) {
                        if(Objects.equals(user.getIP(), socket.getInetAddress().getHostAddress())) {
                            textArea.setText("Вы: " + word);
                        }else{
                            textArea.setText(socket.getInetAddress().getHostAddress() + ": " + word);
                        }
                    }else if(textArea != null) {
                        if(Objects.equals(user.getIP(), socket.getInetAddress().getHostAddress())) {
                            textArea.setText(textArea.getText() + "\nВы: " + word);
                        }else{
                            textArea.setText(textArea.getText() + '\n' + socket.getInetAddress().getHostAddress() + ": " + word);
                        }
                    }
                }
                System.out.println(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
