package server;

import java.net.Socket;

/**
 *
 * @author Antonio Soto
 */
public class EchoThread extends Thread{
    
    protected Socket socket;
    
    public EchoThread( Socket clientSocket ){
        
        this.socket = clientSocket;
    }
    
    @Override
    public void run(){
        // Do the thing
    }
    
}
