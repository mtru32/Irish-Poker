// Created By Mark Truttmann
// May, Summer 2019

import java.util.ArrayList;

public class pokerTests {
  public static void main(String[] args) {
    // System.out.println("testCardRemover(): " + testCardRemover());
    // System.out.println("testCardVal(): " + testCardVal());
    // System.out.println("testDecks(): " + testDecks());
    //testReshuffle();
    //testSuitGuess();
  }

  public static boolean testDecks() {
    boolean pass;
    pokerMain.initDeck();
    pokerMain.generateDeck();
    pokerMain.cardDeck.remove(0);
    pokerMain.cardDeck.remove(0);
    
    if (pokerMain.cardDeck.size() == pokerMain.masterDeck.size()) {
      pass = false;
    } else {
      pass = true;
    }
    pokerMain.generateDeck();
    if (pokerMain.cardDeck.size() != pokerMain.masterDeck.size()) {
      pass = false;
    } else {
      pass = true;
    }
    return pass;
  }

  public static boolean testCardRemover() {
    boolean passed = true;
    pokerMain.initDeck();
    pokerMain.generateDeck();
    ArrayList<String> deck = pokerMain.cardDeck;
    while (deck.size() > 0) {
      pokerMain.genCard();
    }
    if (deck.size() != 0) {
      passed = false;
    }
    return passed;
  }

  public static boolean testCardVal() {
    boolean passed = true;
    if (pokerMain.cardVal("Ace of Spades") != 1) {
      passed = false;
    }
    if (pokerMain.cardVal("King of Diamonds") != 13) {
      passed = false;
    }
    if (pokerMain.cardVal("8 of Diamonds") != 8) {
      passed = false;
    }
    return passed;
  }

  // public static void testReshuffle() {
  //   boolean win;
  //   do {
  //     win = pokerMain.colorGuess();
  //   } while (!win);
  //   {
  //     System.out.println("You won");
  //   }
  // }

  public static void testSuitGuess() {
    pokerMain.initDeck();
    pokerMain.generateDeck();
    pokerMain.suitGuess();
  }

}