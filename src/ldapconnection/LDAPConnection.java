package ldapconnection;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * Example code for retrieving a Users Primary Group from Microsoft Active
 * Directory via. its LDAP API
 *
 * @author Adam Retter <adam.retter@googlemail.com>
 */
public class LDAPConnection {

    private final String ldapAdServer = "ldap://148.209.67.42:389";
    private final String ldapSearchBase = "ou=Facultad de Matematicas, ou=INET, dc=inet, dc=uady, dc=mx";
    private String email = "";
    private String username = "";

    public boolean searchUser(String ldapUsername, String ldapPassword) {

        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        String ldapUsernameCopy= ldapUsername;
        
        if (ldapUsername != null) {
            env.put(Context.SECURITY_PRINCIPAL,
                    "cn=" + ldapUsername + ", " + ldapSearchBase);
        }

        if (ldapPassword != null) {
            env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        }

        try {

            DirContext context = new InitialDirContext(env);
            search(ldapUsernameCopy, context);

            context.close();

            return true;
        } catch (NamingException ex) {

            ex.printStackTrace();
            return false;
        }
    }

    private void search(String ldapUsername, DirContext context) throws NamingException {
        //Recordar que el username tiene que tener toda la ruta de OUs/DCs/CNs
        SearchControls control = new SearchControls();
        control.setReturningObjFlag(true); // Para que devuelva los elementos que buscamos

        String returning[] = new String[2];
        returning[0] = "field1";
        returning[1] = "fields2";
        //Asignamos los atributos a devolver
        control.setReturningAttributes(returning);
        control.setSearchScope(SearchControls.OBJECT_SCOPE);

        String search = ldapUsername; // Search es "en donde buscar" de los directorios del servidor
        String filter = "(sAMAccountName=" + ldapUsername + ")"; // filtro trivial

        NamingEnumeration answer = context.search(search, filter, control);
        SearchResult result = (SearchResult) answer.next(); // Sabemos que habra un solo resultado

        email = result.getAttributes().get("field1").get().toString();      //Buscar nombre de columna
        username = result.getAttributes().get("field2").get().toString();   //para correo y username
//        System.out.println(ldapUsername + ": " + email + ", " + username);
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
