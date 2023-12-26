import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Mediator {
    ArrayList<User> users;

    int port;

    public Mediator(int port){
        users = new ArrayList<>(0);
        this.port = port;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void sendMessage(String message) throws IOException {
        for(User i:users){
            Socket socket = new Socket(i.getIP(), port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            out.flush();
            socket.close();
        }
    }

    public ArrayList<String> getUserIPs(){
        ArrayList<String>ipUsers = new ArrayList<>(0);
        for(User i:users){
            ipUsers.add(i.getIP());
        }
        return ipUsers;
    }

    public ArrayList<String> getUserNames(){
        ArrayList<String>ipUsers = new ArrayList<>(0);
        for(User i:users){
            ipUsers.add(i.getName());
        }
        return ipUsers;
    }

    public String getNameByIP(String ip){
        for(User i:users){
            if(Objects.equals(i.getIP(), ip)){
                return i.getName();
            }
//            System.out.println(i.getIP());
        }
        return "ERROR";
    }
}
