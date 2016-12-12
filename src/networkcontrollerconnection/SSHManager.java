/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkcontrollerconnection;

import com.jcraft.jsch.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SSHManager
 *
 * @author cabbott
 * @version 1.0
 */
public class SSHManager {

    private static final Logger LOGGER
            = Logger.getLogger(SSHManager.class.getName());
    private JSch jschSSHChannel;
    private String strUserName;
    private String strConnectionIP;
    private int intConnectionPort;
    private String strPassword;
    private Session sesConnection;
    private int intTimeOut;

    private void doCommonConstructorActions(String userName,
            String password, String connectionIP, String knownHostsFileName) {
        jschSSHChannel = new JSch();

        try {
            jschSSHChannel.setKnownHosts(knownHostsFileName);
        } catch (JSchException jschX) {
            logError(jschX.getMessage());
        }

        strUserName = userName;
        strPassword = password;
        strConnectionIP = connectionIP;
    }

    public SSHManager(String userName, String password,
            String connectionIP, String knownHostsFileName) {
        doCommonConstructorActions(userName, password,
                connectionIP, knownHostsFileName);
        intConnectionPort = 22;
        intTimeOut = 60000;
    }

    public SSHManager(String userName, String password, String connectionIP,
            String knownHostsFileName, int connectionPort) {
        doCommonConstructorActions(userName, password, connectionIP,
                knownHostsFileName);
        intConnectionPort = connectionPort;
        intTimeOut = 60000;
    }

    public SSHManager(String userName, String password, String connectionIP,
            String knownHostsFileName, int connectionPort, int timeOutMilliseconds) {
        doCommonConstructorActions(userName, password, connectionIP,
                knownHostsFileName);
        intConnectionPort = connectionPort;
        intTimeOut = timeOutMilliseconds;
    }

    public String connect() {
        String errorMessage = null;

        try {
            sesConnection = jschSSHChannel.getSession(strUserName,
                    strConnectionIP, intConnectionPort);
            sesConnection.setPassword(strPassword);
            // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
            sesConnection.setConfig("StrictHostKeyChecking", "no");
            sesConnection.connect(intTimeOut);
        } catch (JSchException jschX) {
            errorMessage = jschX.getMessage();
        }

        return errorMessage;
    }

    private String logError(String errorMessage) {
        if (errorMessage != null) {
            LOGGER.log(Level.SEVERE, "{0}:{1} - {2}",
                    new Object[]{strConnectionIP, intConnectionPort, errorMessage});
        }

        return errorMessage;
    }

    private String logWarning(String warnMessage) {
        if (warnMessage != null) {
            LOGGER.log(Level.WARNING, "{0}:{1} - {2}",
                    new Object[]{strConnectionIP, intConnectionPort, warnMessage});
        }

        return warnMessage;
    }

    public void sendCommand(String command) {
        try {
            Channel channel = this.sesConnection.openChannel("shell");
            
            OutputStream inputstream_for_the_channel = channel.getOutputStream();
            PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
            
            channel.setOutputStream(System.out, true);
            
            channel.connect();
            
            commander.print("rys2016\n");
            commander.print("2016RyS\n");
            commander.print(command + "\n");
            commander.close();
            
            Thread.sleep(1000);
            
            this.close();
        } catch (JSchException ex) {
            Logger.getLogger(SSHManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SSHManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SSHManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        sesConnection.disconnect();
    }

}
