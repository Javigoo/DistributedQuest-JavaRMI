package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProfesorInterface extends Remote{
    public void registerStudent(ClientInterface client) throws RemoteException;
    public void sendNumber(ClientInterface client, int number) throws RemoteException;
}
