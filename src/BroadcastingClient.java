import java.io.IOException;
import java.net.*;

public class BroadcastingClient {
    private static DatagramSocket socket = null;

    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4000);

        socket.send(packet);

        socket.close();
    }
}