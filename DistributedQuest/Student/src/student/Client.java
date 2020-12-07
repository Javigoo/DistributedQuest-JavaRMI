package student;

import common.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 */
public class Client {
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ClientImplementation client = new ClientImplementation();
            ServerInterface stub = (ServerInterface) registry.lookup("Hello");
            stub.registerPlayer(client);
            System.out.println("Client registered, waiting for notification");
            synchronized(client){
                client.wait();
                client.getSecretNumber();
                stub.sendNumber(client, client.secretNumber);
                client.wait();
                System.exit(0);
            }

        } catch (RemoteException e) {
            System.err.println("remote exception: " + e.toString()); e.printStackTrace();
        } catch (Exception e){
            System.err.println("client exception: " + e.toString()); e.printStackTrace();
        }
    }
}
