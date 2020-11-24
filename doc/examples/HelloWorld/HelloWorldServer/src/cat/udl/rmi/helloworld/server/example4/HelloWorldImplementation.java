package cat.udl.rmi.helloworld.server.example4;


import cat.udl.rmi.helloworld.common.HelloWorldInterface;

/**
 * Created by eloigabal on 05/10/2019.
 */
public class HelloWorldImplementation implements HelloWorldInterface {
    public String sayHello() {
        return "Hello, world from different object!, rmiregistry is started in the main class";
    }
}
