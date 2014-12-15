/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts3.mainserver;

import Airhockey.Rmi.IMainLobby;
import Airhockey.Rmi.SerializableChatBox;
import Airhockey.Rmi.SerializableGame;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author stijn
 */
public class MainLobby extends UnicastRemoteObject implements IMainLobby {

    private ArrayList<SerializableGame> busyGames;

    private ArrayList<SerializableGame> waitingGames;

    private SerializableChatBox chatbox;

    private int gameId = 1;

    public MainLobby() throws RemoteException {
        chatbox = new SerializableChatBox();
        busyGames = new ArrayList<>();
        waitingGames = new ArrayList<>();
    }

    @Override
    public ArrayList<SerializableGame> getBusyGames() {
        return busyGames;
    }

    @Override
    public ArrayList<SerializableGame> getWaitingGames() {
        return waitingGames;
    }

    @Override
    public SerializableChatBox getChatBox() {
        return chatbox;
    }

    @Override
    public void addWaitingGame(String description, String hostIP, String username) {
        SerializableGame game = new SerializableGame(gameId, description, hostIP, username);

        waitingGames.add(game);
        gameId++;
    }

    @Override
    public void startGame(SerializableGame game) {
        waitingGames.remove(game);
        busyGames.add(game);
    }

    @Override
    public SerializableGame joinGame(int id) {
        SerializableGame game = null;

        for (SerializableGame waitingGame : waitingGames) {
            if (waitingGame.id == id) {
                game = waitingGame;
                break;
            }
        }

        return game;
    }
}
