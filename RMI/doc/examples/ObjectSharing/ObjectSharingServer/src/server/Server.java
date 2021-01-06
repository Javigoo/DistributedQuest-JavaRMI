package server;

import common.BoardGame;
import common.Card;
import common.Suit;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by eloigabal on 04/11/2020.
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
    public static void main(String[] args){
        try{
            BoardGame game = new BoardGameImplementation();
            startRegistry(null);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("GAME", game);
            synchronized (game) {
                while(true) {
                    System.out.println(game);
                    game.wait();
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
