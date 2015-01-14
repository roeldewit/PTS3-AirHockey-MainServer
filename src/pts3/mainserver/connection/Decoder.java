package pts3.mainserver.connection;

import javafx.application.Platform;
import pts3.mainserver.MainLobby;

/**
 *
 * @author Sam
 */
public class Decoder {

    MainLobby mainLobby;
    
    IConnectionManager connectionManager;

    /**
     * Decoder decodes all incoming messages
     * @param mainlobby
     * @param connectionManager 
     */
    public Decoder(MainLobby mainlobby, IConnectionManager connectionManager) {
        this.mainLobby = mainlobby;
        this.connectionManager = connectionManager;
    }

    protected void receiveCommand(String command) {
        System.out.println("recieved command: " + command);
        String[] splitter = command.split(Protocol.SEPERATOR);

        switch (splitter[0]) {
            case Protocol.CHAT_LINE:
                Platform.runLater(() -> {
                    mainLobby.writeline(splitter[0], splitter[1]);
                });
                break;

            case Protocol.GET_CHATBOX_LINES:
                Platform.runLater(() -> {
                    mainLobby.sendChatbox(connectionManager);
                });
                break;

            case Protocol.GET_CURRENT_OPENGAMES:
                Platform.runLater(() -> {
                    mainLobby.sendWaitingGames(connectionManager);
                });
                break;

            case Protocol.GET_CURRENT_RUNNINGGAMES:
                Platform.runLater(() -> {
                    mainLobby.sendBusyGames(connectionManager);
                });
                break;

            case Protocol.DELETE_GAME:
                Platform.runLater(() -> {
                    mainLobby.deleteGame(Integer.parseInt(splitter[1]));
                });
                break;
            case Protocol.START_GAME:
                Platform.runLater(() -> {
                    mainLobby.startGame(Integer.parseInt(splitter[1]));
                });
                break;
            case Protocol.ADD_NEW_GAME:
                Platform.runLater(() -> {
                    mainLobby.addNewWaitingGame(splitter[1], splitter[2], splitter[3], connectionManager);
                });
                break;
        }
    }

}
