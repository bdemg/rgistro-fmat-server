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
    
    private final SSHManager ConnectionManager = new SSHManager(this.USERNAME, this.PASSWORD, this.IP, "");
    
    private final String ADD_DEVICE_COMMAND = "config macfilter add";
    private final String REMOVE_DEVICE_COMMAND = "config macfilter delete";
    private final String WIFI_NAME = "wifi-rys2016-d";
    private final String WIFI_DESCRIPTION = "\"Red WIFI D RyS 2016\"";
    

    public NetworkControllerConnectionPoint() {
        
        this.ConnectionManager.connect();
    }
    
    
    public void registerMACAddress(String MACAddressToRegister){
        
        this.ConnectionManager.sendCommand(
            this.constructAddMACCommand( MACAddressToRegister )
        );
    }

    
    private String constructAddMACCommand(String MACAddressToRegister) {
        
        return this.ADD_DEVICE_COMMAND + " " +
                MACAddressToRegister + " " +
                this.WIFI_NAME + " " +
                this.WIFI_DESCRIPTION;
    }
    
    
    public void unlistMACAddres(String MACAddressToUnlist){
        
        this.ConnectionManager.sendCommand(
            this.constructRemoveMACCommand( MACAddressToUnlist )
        );
    }
    
    
    private String constructRemoveMACCommand(String MACAddressToUnlist) {
        
        return this.REMOVE_DEVICE_COMMAND + " " +
                MACAddressToUnlist + " " +
                this.WIFI_NAME + " " +
                this.WIFI_DESCRIPTION;
    }
    
    
    public void close(){
        
        this.ConnectionManager.close();
    }

}
