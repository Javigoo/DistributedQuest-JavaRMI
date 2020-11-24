package common;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by eloigabal on 05/10/2019.
 */
public interface HelloWorldServer extends Remote{
    void register(HelloWorldClient client) throws RemoteException;
}
