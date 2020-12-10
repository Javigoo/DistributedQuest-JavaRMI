package student;

import common.Question;
import common.StudentInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */

public class StudentImplementation extends UnicastRemoteObject implements StudentInterface {

    public Queue<Question> questions = new LinkedList();

    public StudentImplementation() throws RemoteException {
        super();
    }

    public void notifyStart() throws RemoteException {
        System.out.println("The exam is going to start");
        synchronized (this) {
            this.notify();
        }
    }

    public void sendQuestion(Question question) throws RemoteException {
        synchronized (this) {
            System.out.printf("Question: " + question.toString() + "\n");
            this.questions.add(question);
            this.notify();
        }
    }

    public void stillConnect() throws RemoteException {

    }

    public Question getNextQuestion() throws RemoteException {
        return this.questions.poll();
    }

    public void notifyAlreadyStarted() throws RemoteException {
        System.out.println("The exam has already started");
        System.exit(0);

    }

    public void notifyEnd(Integer grade) throws RemoteException {
        System.out.println("The exam is over, your grade is " + grade);
        System.exit(1);
    }

}