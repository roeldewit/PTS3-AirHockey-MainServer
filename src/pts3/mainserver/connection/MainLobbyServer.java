/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts3.mainserver.connection;

import com.sun.corba.se.spi.activation.Server;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import static java.lang.Thread.interrupted;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stijn
 */
public class MainLobbyServer extends Thread implements IConnectionManager {

    private final Socket socket;

    private ObjectOutputStream objectOutputStream;

    private Decoder decoder;

    private boolean interrupted = false;

    public MainLobbyServer(Socket socket) throws RemoteException {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting MainLobby");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            while (!interrupted) {
                String command = (String) ois.readObject();
                System.out.println("Received command: " + command);
                decoder.receiveCommand(command);
            }
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(MainLobbyServer.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                objectOutputStream.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(MainLobbyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void sendCommand(String command) {
        try {
            System.out.println("Sending Command: " + command);
            objectOutputStream.writeObject(command);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
