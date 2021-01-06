package cat.udl.rmi.helloworld.server.example1;


import cat.udl.rmi.helloworld.common.HelloWorldInterface;

import java.rmi.RemoteException;

/**
 * Created by eloigabal on 05/10/2019.
 */
public class HelloWorldImplementation implements HelloWorldInterface {
    public String sayHello() throws RemoteException {
        return "Hello, world from different object!";
    }
}
