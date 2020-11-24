package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by eloigabal on 14/10/2019.
 */
public interface ServerInterface extends Remote {
    public void registerPlayer(ClientInterface client) throws RemoteException;
    public void sendNumber(ClientInterface client, int number) throws RemoteException;
}
