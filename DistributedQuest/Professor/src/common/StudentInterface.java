package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface StudentInterface extends Remote {
    public void notifyWinner() throws RemoteException;
    public void notifyLooser() throws RemoteException;
    public void notifyStart() throws RemoteException;
    public void notifyAlreadyStarted() throws RemoteException;
    public void notifyEnd(Integer grade) throws RemoteException;
    public void setQuestion(List<String> question) throws RemoteException;

}
