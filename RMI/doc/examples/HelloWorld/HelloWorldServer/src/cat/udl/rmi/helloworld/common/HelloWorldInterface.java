package cat.udl.rmi.helloworld.common;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by eloigabal on 05/10/2019.
 */
public interface HelloWorldInterface extends Remote{
    String sayHello() throws RemoteException;
}
