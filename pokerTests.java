// Created By Mark Truttmann
// May, Summer 2019

import java.util.ArrayList;

public class pokerTests {
  public static void main(String[] args) {
    // System.out.println("testCardRemover(): " + testCardRemover());
    // System.out.println("testCardVal(): " + testCardVal());
    testReshuffle();
    // testSuitGuess();
  }

  public static boolean testCardRemover() {
    boolean passed = true;

    ArrayList<String> deck = pokerMain.generateDeck();
    while (deck.size() > 0) {
      int cardNum = pokerMain.genCard(deck);
      pokerMain.removeCard(deck, cardNum);
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

  public static void testReshuffle() {
    int win = 0;
    ArrayList<String> cardDeck = new ArrayList<String>();
    do {
      win = pokerMain.playGame(cardDeck);
    } while (win == 0);
    {
      System.out.println("You won");
    }
  }

  public static void testSuitGuess() {
    ArrayList<String> cardDeck = pokerMain.generateDeck();
    pokerMain.suitGuess(cardDeck);
  }

}