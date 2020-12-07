package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ServerInterface extends Remote {
    public void registerPlayer(ClientInterface client) throws RemoteException;
    public void sendNumber(ClientInterface client, int number) throws RemoteException;
}
