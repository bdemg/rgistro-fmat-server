/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrypoints;

import java.util.Scanner;
import ldap.LDAPConnection;

/**
 *
 * @author Jorge A. Cano
 */
public class LDAPtest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Username:");
        String user = input.nextLine();
        System.out.println("Password:");
        String password = input.nextLine();
        
        LDAPConnection LDAP = new LDAPConnection();
        boolean worked = LDAP.searchUser(user, password);
        
        System.out.println(worked);
    }
    
}
