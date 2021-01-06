package cat.udl.rmi.helloworld.server.example1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import cat.udl.rmi.helloworld.common.HelloWorldInterface;
/**
 * Created by eloigabal on 05/10/2019.
 */
public class Server {

    public static void main(String args[]) {
        try {
            HelloWorldImplementation obj = new HelloWorldImplementation();
            HelloWorldInterface remoteobj = (HelloWorldInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", remoteobj);
            System.err.println("Server ready 1");
        } catch (Exception e) {}
    }
}
