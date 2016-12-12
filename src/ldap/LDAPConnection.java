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
    private final String ldapClassName = "com.sun.jndi.ldap.LdapCtxFactory";

    private String userEmail = "";
    private String username = "";

    /**
     * Search in the active directory a user by account and password. If the login is sucessfull sets the full name and email
     * @param ldapUsername the username of user
     * @param ldapPassword the password of the user
     * @return true if the user exist and login sucessfull
     */
    public boolean searchUser(String ldapUsername, String ldapPassword) {
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapClassName);
        env.put(Context.PROVIDER_URL, ldapAdServer);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapUsername + "@inet.uady.mx");
        env.put(Context.SECURITY_CREDENTIALS, ldapPassword);

        try {
            DirContext context = new InitialDirContext(env);
            getUserInfo(ldapUsername, context);
            context.close();
            return true;
        } catch (NamingException ex) {
            return false;
        }
    }

    private void getUserInfo(String ldapUsername, DirContext context) throws NamingException {
        SearchControls control = new SearchControls();
        control.setReturningObjFlag(true);

        String returning[] = {"*"};
        control.setReturningAttributes(returning);
        control.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String filter = "(&(objectClass=user)(sAMAccountName=" + ldapUsername + "))";
        NamingEnumeration answer = context.search(ldapSearchBase, filter, control);
        if (answer.hasMore()) {
            SearchResult result = (SearchResult) answer.next();
            //Search by user the full name and email
            userEmail = result.getAttributes().get("displayName").get().toString();
            username = result.getAttributes().get("mail").get().toString();
        }
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsername() {
        return username;
    }
}
