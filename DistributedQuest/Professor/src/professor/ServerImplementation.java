package professor;

import common.ServerInterface;
import common.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    List<ClientInterface> clients = new ArrayList<ClientInterface>();
    HashMap<ClientInterface,Integer> clientNumber = new HashMap<>();
    int answers = 0;
    int number_of_players=0;
    public ServerImplementation() throws RemoteException{
        super();
    }
    public void registerPlayer(ClientInterface client) throws RemoteException {
        synchronized (this) {
            if(this.clients.size() < 4) {
                clients.add(client);
                this.notify();
            }
        }
    }

    public void sendNumber(ClientInterface client, int number) throws RemoteException{
        synchronized (this) {
            System.out.println(number);
            clientNumber.put(client, number);
            answers ++;
            this.notify();
        }
    }

    public List<ClientInterface> getWinners(int number){
        List<ClientInterface> returns = new ArrayList<>();
        for (ClientInterface client:clients){
            System.out.println(clientNumber.get(client));
            System.out.println(number);

            if (clientNumber.get(client) == number){
                returns.add(client);
            }
        }
        return returns;
    }

    public List<ClientInterface> getLoosers(int number){
        List<ClientInterface> returns = new ArrayList<>();
        for (ClientInterface client:clients){
            if (clientNumber.get(client) != number){
                returns.add(client);
            }
        }
        return returns;
    }

    public void notifyStart(){
        List<ClientInterface> error_clients = new ArrayList<ClientInterface>();
        for (ClientInterface c :clients) {
            try{
                c.notifyStart();
            }catch(RemoteException e){
                System.out.println("Client is not reachable");
                error_clients.add(c);
            }
        }
        for(ClientInterface c: error_clients){
            this.clients.remove(c);
        }
    }

    public int getNumPlayers(){
        return clients.size();
    }

    public void restart(){
        this.clients.clear();
        this.answers = 0;
        this.clientNumber.clear();
    }

}
