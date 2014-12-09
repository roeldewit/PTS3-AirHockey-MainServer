/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts3.mainserver;

import Airhockey.Rmi.SerializableChatBox;
import Airhockey.Rmi.SerializableGame;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author stijn
 */
public class MainLobby extends UnicastRemoteObject {

    private ArrayList<SerializableGame> busyGames;
    
    private ArrayList<SerializableGame> waitingGames;
    
    private SerializableChatBox chatbox;
    
    public MainLobby() throws RemoteException{
        chatbox = new SerializableChatBox();
        busyGames = new ArrayList<>();
        waitingGames = new ArrayList<>();
    }
}
