package student;

import java.rmi.server.UnicastRemoteObject;

public class StudentImplementation extends UnicastRemoteObject implements StudentInterface{

    public StudentImplementation(){
        
    }

    public void setNextQuestion(List<String> question) throws RemoteException{

    }

    public void setGrade(Integer grade) throws RemoteException{

    }
    
}
