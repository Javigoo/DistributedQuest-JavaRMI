package professor;

import common.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Server {
    private static Registry startRegistry(Integer port)
            throws RemoteException {
        if(port == null) {
            port = 1099;
        }
        try {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.list( );
            // The above call will throw an exception
            // if the registry does not already exist
            return registry;
        }
        catch (RemoteException ex) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located ");
            Registry registry= LocateRegistry.createRegistry(port);
            System.out.println("RMI registry created at port ");
            return registry;
        }
    }

    public static void main(String args[]) {
        try {
            Registry registry = startRegistry(null);
            ServerImplementation obj = new ServerImplementation();
            registry.bind("Hello", (ServerImplementation) obj);
            try {
                while(true) {
                    synchronized (obj) {
                        Random r = new Random();
                        int myNumber = r.nextInt(10);
                        System.out.println("number "+myNumber);
                        while (obj.getNumPlayers() < 1) {
                            System.out.println("Players registered " + obj.getNumPlayers());
                            obj.wait();
                        }
                        System.out.println("Starting game");
                        obj.notifyStart();
                        while (obj.answers < obj.getNumPlayers()) {
                            System.out.println("recieved number");
                            obj.wait();
                        }
                        System.out.println("Finishing game");

                        List<ClientInterface> winners = obj.getWinners(myNumber);
                        List<ClientInterface> loosers = obj.getLoosers(myNumber);
                        for (ClientInterface c : winners) {
                            c.notifyWinner();
                        }
                        for (ClientInterface c : loosers) {
                            c.notifyLooser();
                        }
                        obj.restart();
                    }

                }
            } catch (InterruptedException e) {
                System.err.println("Server exception: " + e.toString()); e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString()); e.printStackTrace();
        }
    }

}
