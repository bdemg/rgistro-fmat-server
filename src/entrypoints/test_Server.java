
package entrypoints;

import server.ThreadedEchoServer;

/**
 *
 * @author José
 */
public class test_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ThreadedEchoServer tes = new ThreadedEchoServer();
        tes.initializeServer();
        
    }
    
}
