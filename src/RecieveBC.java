import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

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
                DatagramPacket pack = new DatagramPacket(new byte[5], 5);
                ds.receive(pack);
                if(new String(pack.getData()).equals("hello")){
                    Socket socket = new Socket(pack.getAddress().toString().substring(1), port);
//                    user.getMediator().addUser(new User(pack.getAddress().toString().substring(1), port, false));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(InetAddress.getLocalHost().getHostAddress().toString());
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
