package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by eloigabal on 14/10/2019.
 */
public interface ClientInterface extends Remote {
    public void notifyWinner() throws RemoteException;
    public void notifyLooser() throws RemoteException;
    public void notifyStart() throws RemoteException;
}
