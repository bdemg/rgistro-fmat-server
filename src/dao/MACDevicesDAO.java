/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jorge A. Cano
 */
public class MACDevicesDAO extends DatabaseDAO {
    
    private final String CHECK_USER_DEVICES_QUERY = "SELECT deviceMAC FROM devices WHERE matricula=?";
    private final String CHECK_FOR_USER = "SELECT matricula FROM devices WHERE matricula=?";
    private final String ADD_USER_DEVICE_QUERY = "INSERT INTO devices VALUES (?, ?)";
    private final String REMOVE_USER_DEVICE_QUERY = "DELETE FROM devices WHERE deviceMAC=?";
    
    private final String MAC_COLUMN_NAME = "DeviceMAC";
    
    private static MACDevicesDAO macDevicesDAO;

    private MACDevicesDAO() throws SQLException {
        
        super();
    }
    
    
    public static MACDevicesDAO getMACDevicesDAO() throws SQLException{
        
        if( macDevicesDAO == null ){
            macDevicesDAO = new MACDevicesDAO();
        }
        
        return macDevicesDAO;
    }
    
    public boolean isUserRegistered(String userRegisterNumber) throws SQLException{
        
        PreparedStatement queryStatement = (PreparedStatement) 
            super.connectionToDatabase.prepareStatement( this.CHECK_FOR_USER );
        
        queryStatement.setString(1, userRegisterNumber);
        
        ResultSet queryResults = queryStatement.executeQuery();
        
        boolean  isRegistered = queryResults.last();
        return isRegistered;
    }
    
    
    public int numberOfDevicesRegistered(String userRegisterNumber) throws SQLException{
        
        PreparedStatement queryStatement = (PreparedStatement) 
            super.connectionToDatabase.prepareStatement( this.CHECK_USER_DEVICES_QUERY );
        
        queryStatement.setString(1, userRegisterNumber);
        
        ResultSet queryResults = queryStatement.executeQuery();
        
        queryResults.last();
        return queryResults.getRow();
    }
    
    
    public void addUserDevice(String userRegisterNumber, String deviceMAC) throws SQLException{
        
        PreparedStatement queryStatement = (PreparedStatement) 
            super.connectionToDatabase.prepareStatement( this.ADD_USER_DEVICE_QUERY );
        
        queryStatement.setString(1, userRegisterNumber);
        queryStatement.setString(2, deviceMAC);
        
        queryStatement.execute();
    }
    
    
    public void removeUserDevice(String deviceMAC) throws SQLException{
        
        PreparedStatement queryStatement = (PreparedStatement) 
            super.connectionToDatabase.prepareStatement( this.REMOVE_USER_DEVICE_QUERY );
        
        queryStatement.setString(1, deviceMAC);
        
        queryStatement.execute();
    }
    
    public String[] getUserDevices(String userRegisterNumber) throws SQLException{
        
        PreparedStatement queryStatement = (PreparedStatement) 
            super.connectionToDatabase.prepareStatement( this.CHECK_USER_DEVICES_QUERY );
        
        queryStatement.setString(1, userRegisterNumber);
        
        ResultSet queryResults = queryStatement.executeQuery();
        
        return resultSetMACToArray(queryResults);
    }
    
    
    private String[] resultSetMACToArray(ResultSet resultSet) throws SQLException{
        
        int maxRows = 0;
        if(resultSet.last()){
            maxRows = resultSet.getRow();
            resultSet.beforeFirst();
        }
        
        String[] MACs = new String[maxRows];
        int row = 0;
            
        while(resultSet.next()){
                    
            MACs[ row ] = resultSet.getString( this.MAC_COLUMN_NAME );
                    
            row++;
        }
        
        return MACs;
    }
}
