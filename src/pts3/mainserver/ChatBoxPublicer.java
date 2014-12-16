/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts3.mainserver;

import Airhockey.Rmi.*;
import java.rmi.*;

/**
 *
 * @author pieper126
 */
public class ChatBoxPublicer implements Remote{
    // used to send over chatboxlines
    private SerializableChatBoxLine[] chatboxlines;
    private SerializableChatBoxLine[] oldChatboxlines;

    private BasicPublisher publisher;

    private int consectivePackagesLoss;

    public ChatBoxPublicer(BasicPublisher publisher) throws RemoteException {
        this.publisher = publisher;
        consectivePackagesLoss = 0;
    }

    public void informListeners(SerializableChatBoxLine[] chatboxlines) throws RemoteException {
        oldChatboxlines = chatboxlines.clone();

        try {
            publisher.inform(this, "chatboxLines", oldChatboxlines, chatboxlines);
            consectivePackagesLoss = 0;
        } catch (RemoteException e) {
            consectivePackagesLoss++;
            if (consectivePackagesLoss == 12000) {
                throw new RemoteException();
            }
        }
    }
}
