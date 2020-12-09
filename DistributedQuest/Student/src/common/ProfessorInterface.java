package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ProfessorInterface extends Remote {
    public void joinExam(String id, StudentInterface client) throws RemoteException;
    public void setAnswer(StudentInterface student, int answer) throws RemoteException;

}
