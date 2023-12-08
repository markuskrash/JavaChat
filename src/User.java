import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class User {
    Mediator mediator;

    Socket socket;
    DataOutputStream out;
    BufferedReader in;


    public User(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listener();

    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }


    private void listener() throws IOException {
        while ( true ) {
            System.out.print( "Enter an integer (0 to stop connection, -1 to stop server): " );
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String keyboardInput = br.readLine();
            out.writeBytes( keyboardInput + "\n" );

            int n = Integer.parseInt( keyboardInput );
            if ( n == 0 || n == -1 ) {
                break;
            }

            String responseLine = in.readLine();
            System.out.println("Server returns its square as: " + responseLine);
        }
    }
}
