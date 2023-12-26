import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Objects;

public class User implements Runnable{
    private Mediator mediator;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    boolean running = false;

    private String host;

    private String name;

    private String otherName;
    private int port;

    public User(String name, String otherName, String host, int port, boolean running) throws IOException {
        this.otherName = otherName;
        this.name = name;
        this.host = host;
        this.port = port;
        this.running = running;
    }


    public String getIP(){
        return host;
    }
    public String getName(){
        return name;
    }
    public String getOtherName(){
        return otherName;
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
            try {
                Thread.sleep(500);
                BroadcastingClient.broadcast("hello " + otherName, InetAddress.getByName("255.255.255.255"), port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }



}