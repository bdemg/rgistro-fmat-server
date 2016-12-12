
package entrypoints;

import server.ClientListener;

/**
 *
 * @author José
 */
public class test_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ClientListener tes = new ClientListener();
        tes.initializeServer();
        
    }
    
}
