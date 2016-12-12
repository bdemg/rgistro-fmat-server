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
    protected static final String NETWORK_CONTROLLER_USERNAME = "rys2016";
    protected static final String NETWORK_CONTROLLER_PASSWORD = "2016RyS";
    private final int SSH_PORT = 22;
    
    private final SSHManager ConnectionManager = new SSHManager(this.NETWORK_CONTROLLER_USERNAME, this.NETWORK_CONTROLLER_PASSWORD, this.IP, "", this.SSH_PORT);
    
    private final String ADD_DEVICE_COMMAND = "config macfilter add";
    private final String REMOVE_DEVICE_COMMAND = "config macfilter delete";
    private final String NETWORK_ID_NUMBER = "6";
    private final String INTERFACE_NAME = "wl-labs"; 
    private final String WIFI_DESCRIPTION = "\"Red WIFI D RyS 2016\"";
    

    public NetworkControllerConnectionPoint() {
        
            this.ConnectionManager.connect();
    }
    
    
    public void registerMACAddress(String MACAddressToRegister){
        
        String command = this.constructAddMACCommand( MACAddressToRegister );
        
        this.ConnectionManager.sendCommand(
            command
        );
    }

    
    private String constructAddMACCommand(String MACAddressToRegister) {
        
        return this.ADD_DEVICE_COMMAND + " " +
                MACAddressToRegister + " " +
                this.NETWORK_ID_NUMBER + " " +
                this.INTERFACE_NAME + " " +
                this.WIFI_DESCRIPTION;
    }
    
    
    public void unlistMACAddres(String MACAddressToUnlist){
        
        this.ConnectionManager.sendCommand(
            this.constructRemoveMACCommand( MACAddressToUnlist )
        );
    }
    
    
    private String constructRemoveMACCommand(String MACAddressToUnlist) {
        
        return this.REMOVE_DEVICE_COMMAND + " " + MACAddressToUnlist;
    }
    
    
    public void close(){
        
        this.ConnectionManager.close();
    }

}
