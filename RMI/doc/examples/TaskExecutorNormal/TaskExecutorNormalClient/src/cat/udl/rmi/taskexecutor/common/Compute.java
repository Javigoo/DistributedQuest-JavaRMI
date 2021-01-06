package cat.udl.rmi.taskexecutor.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by eloigabal on 06/10/2019.
 */

public interface Compute extends Remote {
    public <T> T executeTask(Task<T> task) throws RemoteException;
}