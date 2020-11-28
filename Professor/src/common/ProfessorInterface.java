package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProfessorInterface extends Remote{
    public void registerStudent(StudentInterface student) throws RemoteException;
    public void responseAnAnswer(StudentInterface student, Integer choice) throws RemoteException;
}
