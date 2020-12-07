package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ProfessorInterface extends Remote {
    public void registerStudent(String id, StudentInterface client) throws RemoteException;
    public void sendAnswerNumber(StudentInterface client, int number) throws RemoteException;
}

