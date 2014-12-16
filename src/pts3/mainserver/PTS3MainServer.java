/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts3.mainserver;

import Airhockey.Rmi.BasicPublisher;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author stijn
 */
public class PTS3MainServer extends Application {

    private static final int portNumber = 1099;

    private static final String bindingNameMainLobby = "MainLobby";

    private static final String bindingNameChatBoxPublicer = "ChatBoxPublisher";

    private Registry registry = null;
    private MainLobby mainLobby = null;

    private ChatBoxPublicer chatBoxPublicer;

    private BasicPublisher basicPublisher;

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("MainServer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind ChatboxPublicer using registry
        try {
            basicPublisher = new BasicPublisher(new String[]{"chatboxLines"});
            chatBoxPublicer = new ChatBoxPublicer(basicPublisher);
            registry.rebind(bindingNameChatBoxPublicer, chatBoxPublicer);
        } catch (Exception ex) {
            System.out.println("Server: Cannot bind game controller");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
        
        // Bind main lobby using registry
        try {
            mainLobby = new MainLobby(chatBoxPublicer);
            registry.rebind(bindingNameMainLobby, mainLobby);
        } catch (Exception ex) {
            System.out.println("Server: Cannot bind game controller");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
