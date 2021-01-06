package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by eloigabal on 04/11/2020.
 */
public interface BoardGame extends Remote {
    public Card getCard() throws RemoteException;
    public void sendCard(Card c) throws RemoteException;
}
