package server;

import dao.MACDevicesDAO;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import ldapconnection.LDAPConnection;

/**
 *
 * @author Antonio Soto
 */
public class EchoThread extends Thread{
    
    protected Socket socket;
    protected BufferedReader buffReader;
    protected DataOutputStream out;
    
    public EchoThread( Socket clientSocket ){
        
        this.socket = clientSocket;
    }
    
    @Override
    public void run(){
        try {
            // Do the thing
            InputStream in = this.socket.getInputStream();
            this.buffReader = new BufferedReader(new InputStreamReader(in));
            
            String username;
            while((username = buffReader.readLine())!=null){
                break;
            }
            
            String password;
            while((password = buffReader.readLine())!=null){
                break;
            }
            
//            LDAPConnection ldap = new LDAPConnection();
//            boolean isFound = ldap.searchUser(username, password);
//            
            out = new DataOutputStream(this.socket.getOutputStream());
            out.writeBoolean(true);
            
            if(true){
                //Se lee y se manda el email
                
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
            
            String code;
//            while((code = buffReader.readLine())!=null){
//                break;
//            }
            
            do{
                code = buffReader.readLine();
            }while(code==null);
            
            switch(code){
                
                case ServiceCodes.REGISTER_MAC:
                    this.registerMac();
                    break;
                
                case ServiceCodes.REQUEST_REGISTERED_MACS:
                    this.requestRegisteredMac();
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
            while((registerNumber = buffReader.readLine())!=null){
                break;
            }
            
            boolean isNumberOfDevicesInRange = MACDevicesDAO.getMACDevicesDAO().numberOfDevicesRegistered(registerNumber) < 2;
            if( isNumberOfDevicesInRange ){
                
                String deviceMac = buffReader.readLine();
                MACDevicesDAO.getMACDevicesDAO().addUserDevice(registerNumber, deviceMac);
                
                // *** Conexión con el controlador aún no funciona ***
//                NetworkControllerConnectionPoint nwc = new NetworkControllerConnectionPoint();
//                nwc.registerMACAddress(registerNumber);
//                nwc.close();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    private void requestRegisteredMac() {
        
    }

    private void unlistMac() {
        
    }
    
}
