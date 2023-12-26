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
//        serverSocket = new ServerSocket(port);
//        clientSocket = new Socket(host, port);
//        clientSocket = serverSocket.accept();
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.otherName = otherName;
        this.name = name;
        this.host = host;
        this.port = port;
        this.running = running;
        String whost = "";
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if(displayBroadcastAddress(networkInterface) != null) {
                whost = displayBroadcastAddress(networkInterface);
            }
        }
        BroadcastingClient.broadcast("hello " + otherName, InetAddress.getByName(whost), port);

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
        }

    }


    private static String displayBroadcastAddress(NetworkInterface networkInterface) throws SocketException, UnknownHostException {
        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

        while (inetAddresses.hasMoreElements()) {
            InetAddress inetAddress = inetAddresses.nextElement();
            if (Objects.equals(InetAddress.getLocalHost().getHostAddress(), inetAddress.getHostAddress())) {

                try {
                    InterfaceAddress interfaceAddress = networkInterface.getInterfaceAddresses().get(0);
                    InetAddress broadcastAddress = interfaceAddress.getBroadcast();
                    if (broadcastAddress != null) {
                        return broadcastAddress.getHostAddress();
                    } else {
                        System.out.println("Широковещательный адрес неизвестен.");
                    }
                } catch (NullPointerException e) {
                    System.out.println("Информация о подсети неизвестна.");
                }
            }
        }
        return null;
    }

}