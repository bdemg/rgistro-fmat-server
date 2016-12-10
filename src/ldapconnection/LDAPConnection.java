package ldapconnection;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Example code for retrieving a Users Primary Group from Microsoft Active
 * Directory via. its LDAP API
 *
 * @author Adam Retter <adam.retter@googlemail.com>
 */
public class LDAPConnection {

    private final String ldapAdServer = "ldap://148.209.67.42:389";
    private final String ldapSearchBase = "ou=Facultad de Matematicas, ou=INET, dc=inet, dc=uady, dc=mx";

    public boolean searchUser(String ldapUsername, String ldapPassword) {

        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        
        if (ldapUsername != null) {
            env.put(Context.SECURITY_PRINCIPAL,
                    "cn=" + ldapUsername +", " + ldapSearchBase);
        }
        
        if (ldapPassword != null) {
            env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        }

        try {

            DirContext contex = new InitialDirContext(env);
            contex.close();
            
            return true;
        } catch (NamingException ex) {
            
            ex.printStackTrace();
            return false;
        }

    }
}
