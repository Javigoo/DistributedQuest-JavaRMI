package student;

import java.rmi.server.UnicastRemoteObject;

public class StudentImplementation extends UnicastRemoteObject implements StudentInterface{
    List<String> questions = new ArrayList<String>();
    String universityID;

    public StudentImplementation(){
        
    }

    public void setNextQuestion(List<String> question) throws RemoteException{

    }

    public void setGrade(Integer grade) throws RemoteException{

    }
    
}
