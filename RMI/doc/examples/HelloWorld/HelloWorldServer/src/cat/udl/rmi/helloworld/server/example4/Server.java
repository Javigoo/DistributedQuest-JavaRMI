package cat.udl.rmi.helloworld.server.example4;
import cat.udl.rmi.helloworld.common.HelloWorldInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by eloigabal on 05/10/2019.
 */

public class Server {
    private static Registry startRegistry(int RMIPortNum)
            throws RemoteException {
        try {
            Registry registry= LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );
            // The above call will throw an exception
            // if the registry does not already exist
            return registry;
        }
        catch (RemoteException ex) {
            // No valid registry at that port.
            System.out.println(
                    "RMI registry cannot be located at port " + RMIPortNum);
            Registry registry= LocateRegistry.createRegistry(RMIPortNum);
            System.out.println(
                    "RMI registry created at port " + RMIPortNum);
            return registry;
        }
    }

    public static void main(String args[]) {
        try {
            HelloWorldImplementation obj = new HelloWorldImplementation();
            HelloWorldInterface remoteobj = (HelloWorldInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = startRegistry(1098);
            registry.bind("Hello", remoteobj);
            System.err.println("Server ready 4, rmi_registry started automatically");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString()); e.printStackTrace();
        }
    }
}

