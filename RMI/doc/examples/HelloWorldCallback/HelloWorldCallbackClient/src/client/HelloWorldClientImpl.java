package client;

import common.HelloWorldClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by eloigabal on 12/10/2019.
 */
public class HelloWorldClientImpl extends UnicastRemoteObject implements HelloWorldClient{
    public HelloWorldClientImpl() throws RemoteException {}

    public void notifyHello() throws RemoteException{
        System.out.println("Client recieved \"hello world\" message from server");
    }

}
