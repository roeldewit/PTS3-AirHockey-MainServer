package pts3.mainserver;

import Airhockey.Rmi.SerializableChatBox;
import Airhockey.Rmi.SerializableGame;
import java.util.ArrayList;

/**
 *
 * @author Roel
 */
public interface IMainLobby {

    void addWaitingGame(String description, String hostIP, String username);

    ArrayList<SerializableGame> getBusyGames();

    SerializableChatBox getChatBox();

    ArrayList<SerializableGame> getWaitingGames();

    SerializableGame joinGame(int id);

    void startGame(SerializableGame game);

}
