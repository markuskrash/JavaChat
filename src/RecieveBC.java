import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class RecieveBC implements Runnable{
    private boolean running;

    private int port;

    User user;

    public RecieveBC(boolean running, int port, User user){
        this.running = running;
        this.port = port;
        this.user = user;
    }
    @Override
    public void run() {
        try {
            DatagramSocket ds = new DatagramSocket(port);
            while (running) {
                DatagramPacket pack = new DatagramPacket(new byte[255], 255);
                ds.receive(pack);
                String word = new String(pack.getData());
                int i = 5;
                while(i < 255){
                    if(!Objects.equals(word.charAt(i), word.charAt(254))){
                        i++;
                    }else{
                        break;
                    }
                }
                if(word.startsWith("hello")){
                    Socket socket = new Socket(pack.getAddress().toString().substring(1), port);
                    user.getMediator().addUser(new User(word.substring(6, i), word.substring(6, i), pack.getAddress().toString().substring(1), port, false));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(user.getOtherName());
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
