package server;

import common.HelloWorldClient;
import common.HelloWorldServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by eloigabal on 05/10/2019.
 */

public class HelloWorldImplementation extends UnicastRemoteObject implements HelloWorldServer {

    public HelloWorldImplementation() throws RemoteException {}

    private ArrayList<HelloWorldClient> clients = new ArrayList<HelloWorldClient>();

    public void register(HelloWorldClient client){
        System.out.println("Registering client");
        this.clients.add(client);
    }

    public void notify_clients(){
        for (HelloWorldClient c:this.clients){
            try {
                System.out.println("calling the client");
                c.notifyHello();
            }catch(RemoteException e){
                System.out.println("error in call");
            }
        }
    }
}
