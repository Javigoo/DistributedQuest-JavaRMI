package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ProfessorInterface extends Remote {
    void joinExam(String id, StudentInterface client) throws RemoteException;

    void setAnswer(StudentInterface student, int answer) throws RemoteException;


}