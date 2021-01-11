package common;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface StudentInterface extends Remote {
    void notifyStart() throws RemoteException;

    void notifyAlreadyStarted() throws RemoteException;

    void notifyEnd(Integer grade) throws RemoteException;

    void sendQuestion(Question question) throws RemoteException;

    void stillConnect() throws RemoteException;

    void notifyInvalidID() throws RemoteException;
}


