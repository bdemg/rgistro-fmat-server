package server;

import dao.MACDevicesDAO;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import ldap.LDAPConnection;
import networkcontrollerconnection.NetworkControllerConnectionPoint;

/**
 *
 * @author Antonio Soto
 */
public class RegistryServerThread extends Thread{
    
    protected Socket socket;
    protected BufferedReader clientInputReader;
    protected DataOutputStream dataOutput;
    
    private final String EMPTY = " ";
    
    private final int MAX_NUMBERED_OF_REGISTERED_DEVICES = 2;
    
    private final int TWO_REGISTERED_MACS = 2;
    private final int ONE_REGISTERED_MAC = 1;
    
    private final int FIRST_MAC = 0;
    private final int SECOND_MAC = 1;
    
    public RegistryServerThread( Socket clientSocket ){
        
        this.socket = clientSocket;
    }
    
    @Override
    public void run(){
        
        try {
            
            this.clientInputReader = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream())
            );
            
            String username;
            while((username = this.clientInputReader.readLine())!=null){
                break;
            }
            
            String password;
            while((password = this.clientInputReader.readLine())!=null){
                break;
            }
            
            LDAPConnection ldap = new LDAPConnection();
            boolean isFound = ldap.searchUser(username, password);
            
            dataOutput = new DataOutputStream(this.socket.getOutputStream());
            dataOutput.writeBoolean(isFound);
            
            if(isFound){
                
                PrintWriter outputSocket = new PrintWriter(this.socket.getOutputStream());
                
                outputSocket.println(ldap.getUsername());
                outputSocket.flush();
                outputSocket.println(ldap.getUserEmail());
                outputSocket.flush();
                
                this.mainLoop();
            }
            else{
                this.socket.close();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void mainLoop() throws IOException {
        
        while(true){
            
            String serviceCode;
            do{
                serviceCode = this.clientInputReader.readLine();
            }while(serviceCode==null);
            
            switch(serviceCode){
                
                case ServiceCodes.REGISTER_MAC:
                    this.registerMac();
                    break;
                
                case ServiceCodes.REQUEST_REGISTERED_MACS:
                    this.requestRegisteredMacs();
                    break;
                
                case ServiceCodes.UNLIST_MAC:
                    this.unlistMac();
                    break;
                
                default:
                    break;
            }
        }
    }

    private void registerMac() {
        
        try {
            
            String registerNumber; 
            while((registerNumber = this.clientInputReader.readLine())!=null){
                break;
            }
            
            boolean isNumberOfDevicesInRange = MACDevicesDAO.getMACDevicesDAO().
                    numberOfDevicesRegistered(registerNumber) < 
                    this.MAX_NUMBERED_OF_REGISTERED_DEVICES;
            
            if( isNumberOfDevicesInRange ){
                
                String deviceMac = this.clientInputReader.readLine();
                MACDevicesDAO.getMACDevicesDAO().addUserDevice(registerNumber, deviceMac);
                
                NetworkControllerConnectionPoint controllerConnection = 
                        new NetworkControllerConnectionPoint();
                controllerConnection.registerMACAddress(deviceMac);
                controllerConnection.close();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    private void requestRegisteredMacs() throws IOException {
        
        try {
            
            String userRegisterNumber;
            do{
                userRegisterNumber = this.clientInputReader.readLine();
            }while(userRegisterNumber == null);
            
            String[] registeredMacs = MACDevicesDAO.getMACDevicesDAO().getUserDevices(userRegisterNumber);
            
            PrintWriter outputSocket = new PrintWriter(this.socket.getOutputStream());
            
            if( registeredMacs.length == this.TWO_REGISTERED_MACS ){
                
                outputSocket.println( registeredMacs[ this.FIRST_MAC ] );
                outputSocket.flush();
                
                outputSocket.println( registeredMacs[ this.SECOND_MAC ] );
                outputSocket.flush();
            }
            else if( registeredMacs.length == this.ONE_REGISTERED_MAC ){
                
                outputSocket.println( registeredMacs[ this.FIRST_MAC ] );
                outputSocket.flush();
                
                outputSocket.println( this.EMPTY );
                outputSocket.flush();
            }
            else{
                
                outputSocket.println( this.EMPTY );
                outputSocket.flush();
                
                outputSocket.println( this.EMPTY );
                outputSocket.flush();
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void unlistMac() throws IOException {
        
        try {
            
            String deviceMac;
            do{
                deviceMac = this.clientInputReader.readLine();
            }while(deviceMac == null);
            
            MACDevicesDAO.getMACDevicesDAO().removeUserDevice(deviceMac);
            
            NetworkControllerConnectionPoint controllerConnection = new NetworkControllerConnectionPoint();
            controllerConnection.unlistMACAddres(deviceMac);
            controllerConnection.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
