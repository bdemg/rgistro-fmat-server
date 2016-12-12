
package entrypoints;

import server.ClientListener;

/**
 *
 * @author José
 */
public class EntryPoint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ClientListener clientListener = new ClientListener();
        clientListener.initializeServer();
        
    }
    
}
