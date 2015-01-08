/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts3.mainserver.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pieper126
 */
public class ConnectionListener extends Thread {

    private boolean acceptMore = true;

    public ConnectionListener() {
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            System.out.println("Starting connection listener...");
            serverSocket = new ServerSocket(8189);

            while (acceptMore) {
                Socket socket = serverSocket.accept();
                System.out.println("Server bound");        
                
                MainLobbyServer server = new MainLobbyServer(socket);                          
            }

        } catch (IOException e) {
            Logger.getLogger(MainLobbyServer.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ConnectionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cancel() {
        acceptMore = false;
        System.out.println("ConnectionListener cancelled");
    }

}