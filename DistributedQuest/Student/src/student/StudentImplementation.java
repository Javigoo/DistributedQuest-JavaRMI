package student;

import common.Question;
import common.StudentInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 */

public class StudentImplementation extends UnicastRemoteObject implements StudentInterface {

    private int secretNumber = 0;
    private Queue<Question> questions;

    public StudentImplementation() throws RemoteException {
        super();
    }

    public void notifyWinner() throws RemoteException {
        System.out.println("Winner");
        synchronized (this) {
            this.notify();
        }

    }

    public void notifyLooser() throws RemoteException {
        System.out.println("Looser");
        synchronized (this) {
            this.notify();
        }
    }

    public void getSecretNumber() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter choice number:");
        secretNumber = keyboard.nextInt();
    }

    public void notifyStart() throws RemoteException {
        System.out.println("The exam is going to start");
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("enter an integer");
//        secretNumber = keyboard.nextInt();
        synchronized (this) {
            this.notify();
        }
    }

    public void sendQuestion(Question question) throws RemoteException {
        synchronized (this) {
            System.out.printf(question.toString() + "\n");
            //this.questions.add(question);
            this.notify();
        }
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