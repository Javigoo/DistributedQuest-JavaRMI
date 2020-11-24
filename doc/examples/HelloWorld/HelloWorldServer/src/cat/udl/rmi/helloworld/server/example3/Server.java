package cat.udl.rmi.helloworld.server.example3;

import cat.udl.rmi.helloworld.common.HelloWorldInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by eloigabal on 05/10/2019.
 */
public class Server extends UnicastRemoteObject implements HelloWorldInterface {
    public Server () throws RemoteException {
        super();
    }
    public String sayHello() {
        return "Hello, world! from same object, implementing unicastremoteobject";
    }

    public static void main(String args[]) {
        try {
            HelloWorldInterface obj = new Server();
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello3", obj);
            System.err.println("Server ready 3");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString()); e.printStackTrace();
        }
    }
}
