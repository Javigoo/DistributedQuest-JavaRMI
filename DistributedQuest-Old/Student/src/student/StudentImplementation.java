package student;

import common.StudentInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class StudentImplementation extends UnicastRemoteObject implements StudentInterface{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<String[]> questions = new ArrayList<String[]>();
    private String universityID;
    private Integer grade;

    public StudentImplementation(String universityID) throws RemoteException {
        this.universityID = universityID;
    }

    public void setNextQuestion(String[] question) throws RemoteException {
        synchronized(this) {
            this.questions.add((String[]) question);
            this.notify();
        }
    }

    public void setGrade(Integer grade) throws RemoteException {
        synchronized(this) {
            this.grade = grade;
            this.notify();
        }
    }
    
    public String getUniversityID() {
        return this.universityID;
    }

    public Integer grade() {
        return this.grade;
    }
}
