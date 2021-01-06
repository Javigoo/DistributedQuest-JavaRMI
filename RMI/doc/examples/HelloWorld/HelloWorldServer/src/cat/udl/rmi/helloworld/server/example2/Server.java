package cat.udl.rmi.helloworld.server.example2;

import cat.udl.rmi.helloworld.common.HelloWorldInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by eloigabal on 05/10/2019.
 */
public class Server implements HelloWorldInterface {

    public String sayHello() {
        return "Hello, world! from same object";
    }

    public static void main(String args[]) {
        try {
            Server obj = new Server();
            HelloWorldInterface remoteobj = (HelloWorldInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello2", remoteobj);
            System.err.println("Server ready 2");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString()); e.printStackTrace();
        }
    }
}
