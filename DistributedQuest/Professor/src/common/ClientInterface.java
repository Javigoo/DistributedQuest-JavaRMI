package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ClientInterface extends Remote {
    public void notifyWinner() throws RemoteException;
    public void notifyLooser() throws RemoteException;
    public void notifyStart() throws RemoteException;
}
