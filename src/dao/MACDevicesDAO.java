/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;

/**
 *
 * @author Jorge A. Cano
 */
public class MACDevicesDAO extends DatabaseDAO {
    
    private final String CHECK_USER_DEVICES_QUERY = "SELECT deviceMAC FROM devices WHERE matricula=?";
    private final String ADD_USER_DEVICE_QUERY = "INSERT INTO devices VALUES (?, ?)";
    private final String REMOVE_USER_DEVICE_QUERY = "DELETE FROM devices WHERE deviceMAC=?";

    public MACDevicesDAO() throws SQLException {
        super();
    }
    
    
    
}
