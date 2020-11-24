package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by eloigabal on 12/10/2019.
 */
public interface HelloWorldClient extends Remote {

    public void notifyHello() throws RemoteException;
}
