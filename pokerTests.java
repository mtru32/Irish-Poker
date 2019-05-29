// Created By Mark Truttmann
// May, Summer 2019

import java.util.ArrayList;

public class pokerTests {
    public static void main(String[] args){
        //testCardRemover();
        //testCardVal();
        //testReshuffle();
    }

    public static void testCardRemover() {
        ArrayList<String> deck = pokerMain.generateDeck();
        System.out.println(deck);
        while (deck.size() > 0) {
            int cardNum = pokerMain.genCard(deck);
            System.out.println(pokerMain.removeCard(deck, cardNum));
        }         
    }
    public static void testCardVal() {
        System.out.println(pokerMain.cardVal("Ace of Spades"));
        System.out.println(pokerMain.cardVal("King of Diamonds"));
        System.out.println(pokerMain.cardVal("8 of Diamonds"));
    }
    public static void testReshuffle() {
        int win = 0;
        ArrayList <String> cardDeck = pokerMain.generateDeck();
        do {
            win = pokerMain.playGame(cardDeck);
        }
        while (win == 0); {
            System.out.println("You won");
        }
    }
}
