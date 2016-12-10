/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrypoints;

import dao.MACDevicesDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge A. Cano
 */
public class DatabaseTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            MACDevicesDAO dao = MACDevicesDAO.getMACDevicesDAO();
            
            dao.addUserDevice("a14016339", "98:6b:3d:0d:b3:61");
            dao.addUserDevice("a14016339", "f0:92:1c:b2:f3:04");
            
            System.out.println(dao.numberOfDevicesRegistered("a14016339"));
            
            dao.removeUserDevice("f0:92:1c:b2:f3:04");
            dao.removeUserDevice("98:6b:3d:0d:b3:61");
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
