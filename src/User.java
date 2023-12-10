import java.io.*;
import java.net.*;

public class User implements Runnable{
    private Mediator mediator;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    boolean running = false;

    private String host;
    private int port;

    public User(String host, int port, boolean running) throws IOException {
//        serverSocket = new ServerSocket(port);
//        clientSocket = new Socket(host, port);
//        clientSocket = serverSocket.accept();
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.host = host;
        this.port = port;
        this.running = running;
        BroadcastingClient.broadcast("hello", InetAddress.getLocalHost(), 8080);

    }

    public String getIP(){
        return host;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void sendMessage(String message) throws IOException {
        mediator.sendMessage(message);
    }




    @Override
    public void run() {
        if(running) {
            RecieveS recieveS = new RecieveS(running, port, this);
            Thread threadS = new Thread(recieveS);
            threadS.start();
            RecieveBC recieveBC = new RecieveBC(running, port, this);
            Thread threadBC = new Thread(recieveBC);
            threadBC.start();
        }

    }

}