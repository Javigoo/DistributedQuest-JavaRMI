package cat.udl.rmi.helloworld.client;
import cat.udl.rmi.helloworld.common.HelloWorldInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by eloigabal on 05/10/2019.
 */
public class Client {
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloWorldInterface stub = (HelloWorldInterface) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); e.printStackTrace();
        }
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloWorldInterface stub = (HelloWorldInterface) registry.lookup("Hello2");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); e.printStackTrace();
        }
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloWorldInterface stub = (HelloWorldInterface) registry.lookup("Hello3");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); e.printStackTrace();
        }
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1098);
            HelloWorldInterface stub = (HelloWorldInterface) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); e.printStackTrace();
        }
    }
}

