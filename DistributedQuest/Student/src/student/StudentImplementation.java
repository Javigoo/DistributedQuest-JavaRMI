package student;

import common.StudentInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 *
 */
public class StudentImplementation extends UnicastRemoteObject implements StudentInterface {
    int secretNumber = 0;
    public StudentImplementation() throws RemoteException{
        super();
    }

    public void notifyWinner() throws RemoteException{
        System.out.println("Winner");
        synchronized (this){
            this.notify();
        }

    }
    public void notifyLooser() throws RemoteException{
        System.out.println("Looser");
        synchronized (this){
            this.notify();
        }
    }
    public void getSecretNumber(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("enter an integer");
        secretNumber = keyboard.nextInt();
    }

    public void notifyStart() throws RemoteException{
        System.out.println("The exam is going to start");
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("enter an integer");
//        secretNumber = keyboard.nextInt();
        synchronized (this) {
            this.notify();
        }


    }
}
