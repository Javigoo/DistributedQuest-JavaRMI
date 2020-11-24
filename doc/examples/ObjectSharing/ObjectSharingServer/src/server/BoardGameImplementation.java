package server;

import common.BoardGame;
import common.Card;
import common.Suit;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by eloigabal on 04/11/2020.
 */
public class BoardGameImplementation extends UnicastRemoteObject implements BoardGame {

    List<Card> deck = null;
    List<Card> played = null;

    public BoardGameImplementation () throws RemoteException {
        super();
        this.deck = new ArrayList<>();
        this.played = new ArrayList<>();
        for(Suit s : Suit.values()){
            for(int j=1;j<=12;j++){
                deck.add(new Card(s, j));
            }
        }
        Collections.shuffle(this.deck);
    }

    @Override
    public synchronized Card getCard() throws RemoteException {
        try {
            Card c = this.deck.remove(0);
            this.notify();
            return c;
        }catch(Exception e){
            this.notify();
            return null;
        }

    }

    @Override
    public synchronized void sendCard(Card c) throws RemoteException{
        this.played.add(c);
        this.notify();
    }

    public String toString(){
        return String.format("Cards in deck %d\nDeck: %s\nPlayed: %s", this.deck.size(), this.deck, this.played);
    }
}
