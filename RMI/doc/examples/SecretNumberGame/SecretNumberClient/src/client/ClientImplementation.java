package client;

import common.ClientInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by eloigabal on 15/10/2019.
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {
    int secretNumber = 0;
    public ClientImplementation() throws RemoteException{
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
        System.out.println("The game is going to start");
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("enter an integer");
//        secretNumber = keyboard.nextInt();
        synchronized (this) {
            this.notify();
        }


    }
}
