package student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StudentImplementation extends UnicastRemoteObject implements StudentInterface{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<ArrayList> questions = new ArrayList<ArrayList>();
    private String universityID;
    private Integer grade;

    public StudentImplementation(String universityID) throws RemoteException {
        this.universityID = universityID;
    }

    public void setNextQuestion(List<String> question) throws RemoteException {
        synchronized(this) {
            this.questions.add(question);
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
