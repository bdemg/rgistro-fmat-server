/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkcontrollerconnection;

/**
 *
 * @author jorge
 */
public class NetworkControllerConnectionPoint {
    
    private final String IP = "192.168.69.18";
    private final String USERNAME = "rys2016";
    private final String PASSWORD = "2016RyS";
    private final int SSH_PORT = 22;
    
    private final SSHManager ConnectionManager = new SSHManager(this.USERNAME, this.PASSWORD, this.IP, "", this.SSH_PORT);
    
    private final String ADD_DEVICE_COMMAND = "config macfilter add";
    private final String REMOVE_DEVICE_COMMAND = "config macfilter delete";
    private final String NETWORK_ID_NUMBER = "6";
    private final String WIFI_NAME = "wifi-rys2016-d"; 
    private final String WIFI_DESCRIPTION = "\"Red WIFI D RyS 2016\"";
    

    public NetworkControllerConnectionPoint() {
        
        System.out.println(this.ConnectionManager.connect());
    }
    
    
    public String registerMACAddress(String MACAddressToRegister){
        
        return this.ConnectionManager.sendCommand(
            this.constructAddMACCommand( MACAddressToRegister )
        );
    }

    
    private String constructAddMACCommand(String MACAddressToRegister) {
        
        return this.ADD_DEVICE_COMMAND + " " +
                MACAddressToRegister + " " +
                this.NETWORK_ID_NUMBER + " " +
                this.WIFI_NAME + " " +
                this.WIFI_DESCRIPTION;
    }
    
    
    public String unlistMACAddres(String MACAddressToUnlist){
        
        return this.ConnectionManager.sendCommand(
            this.constructRemoveMACCommand( MACAddressToUnlist )
        );
    }
    
    
    private String constructRemoveMACCommand(String MACAddressToUnlist) {
        
        return this.REMOVE_DEVICE_COMMAND + " " +
                MACAddressToUnlist + " " +
                this.NETWORK_ID_NUMBER + " " +
                this.WIFI_NAME + " " +
                this.WIFI_DESCRIPTION;
    }
    
    
    public void close(){
        
        this.ConnectionManager.close();
    }

}
