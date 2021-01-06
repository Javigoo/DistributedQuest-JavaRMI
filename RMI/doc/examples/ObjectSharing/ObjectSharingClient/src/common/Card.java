package common;

import java.io.Serializable;

/**
 * Created by eloigabal on 04/11/2020.
 */
public class Card implements Serializable {
    Suit suit;
    int value;
    public Card(Suit suit, int value){
        this.suit = suit;
        this.value = value;
    }

    public String toString(){
        return this.value + " " + this.suit;
    }
}
