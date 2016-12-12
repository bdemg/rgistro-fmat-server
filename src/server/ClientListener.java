package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Antonio Soto
 */
public class ClientListener {

    private static final int PORT = 8086;

    public void initializeServer() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Socket socket = null;
        while (true) {
            
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            
            // Creates new thread for a client.
            new RegistryServerThread(socket).start();
        }
    }

}
