/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrypoints;

import java.util.Arrays;
import networkcontrollerconnection.NetworkControllerConnectionPoint;

/**
 *
 * @author Jorge A. Cano
 */
public class SSHTest {
    
    public static void main(String[] args) {
        
        NetworkControllerConnectionPoint NWC = new NetworkControllerConnectionPoint();
        
        NWC.registerMACAddress("F8:A9:D0:7F:EA:0E");
        
        NWC.close();
    }
            
}
