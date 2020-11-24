package client;

import common.BoardGame;
import common.Card;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by eloigabal on 04/11/2020.
 */
public class Client {

    public static void main(String args[]){
        try{
            Registry registry = LocateRegistry.getRegistry();
            BoardGame game = (BoardGame) registry.lookup("GAME");
            Card c = game.getCard();
            System.out.println(c);
            game.sendCard(c);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
