package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ProfessorInterface extends Remote {
    public void registerStudent(StudentInterface client, String id) throws RemoteException;
    public void sendAnswerNumber(StudentInterface client, int number) throws RemoteException;
}

