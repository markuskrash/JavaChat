import java.io.IOException;
import java.net.*;

public class BroadcastingClient implements Runnable{
    private static DatagramSocket socket = null;
    String broadcastMessage;
    InetAddress address;
    int port;

    public BroadcastingClient(String broadcastMessage, InetAddress address, int port){
        this.broadcastMessage = broadcastMessage;
        this.address = address;
        this.port = port;
    }

    public static void broadcast(String broadcastMessage, InetAddress address, int port) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);

        socket.close();
    }

    @Override
    public void run() {
        while(true){
            try {
                BroadcastingClient.broadcast(broadcastMessage, address, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}