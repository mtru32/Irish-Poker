// Created By Mark Truttmann
// May, Summer 2019

import java.util.ArrayList;

public class pokerTests {
  public static void main(String[] args) {
    System.out.println("testCardVal(): " + testCardVal());
    System.out.println("testDecks(): " + testDecks());
    System.out.println("testReshuffle(): " + testReshuffle());
    testSuitGuess();
  }

  private static boolean testDecks() {
    boolean pass;
    pokerMain.checkDeck();
    pokerMain.cardDeck.remove(0);
    pokerMain.cardDeck.remove(0);

    
    if (pokerMain.cardDeck.size() == pokerMain.masterDeck.size()) {
      pass = false;
    } else {
      pass = true;
    }
    pokerMain.genDeck();
    if (pokerMain.cardDeck.size() != pokerMain.masterDeck.size()) {
      pass = false;
    } else {
      pass = true;
    }
    return pass;
  }

  private static boolean testCardVal() {
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

  private static boolean testReshuffle() {
    boolean pass = true;
    ArrayList<String> deck = pokerMain.cardDeck;

    pokerMain.checkDeck();

    while (deck.size() > 0) {
      pokerMain.genCard();
    }
    
    pokerMain.checkDeck();
    if (deck.size() != 52) {
      return false;
    }
    return pass;
  }

  private static void testSuitGuess() {
    pokerMain.checkDeck();
    System.out.println("\n**Press q to end test**");
    while (!pokerMain.suitGuess());
  }

}