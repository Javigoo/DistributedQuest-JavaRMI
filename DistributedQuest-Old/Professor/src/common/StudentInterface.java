package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StudentInterface extends Remote {

    public void setNextQuestion(List<String> question) throws RemoteException;
    public void setGrade(Integer grade) throws RemoteException;
}
