// Created By Mark Truttmann
// Summer 2019
// Edited Winter 2020

import java.util.ArrayList;

public class pokerTests {
  public static void main(String[] args) {
    // System.out.println("testCardVal(): " + testCardVal());
    // System.out.println("testDecks(): " + testDecks());
    // System.out.println("testReshuffle(): " + testReshuffle());
    // testSuitGuess();
    // testInputLoop();
  }

  private static void testInputLoop() {
    String prompt = "In or out? [i/o]";
    ArrayList<String> validInput = new ArrayList<String>();
    validInput.add("i");
    validInput.add("o");
    pokerMain.inputLoop(validInput, prompt);
  }

  private static boolean testDecks() {
    pokerMain.checkDeck();
    pokerMain.cardDeck.remove(0);
    pokerMain.cardDeck.remove(0);

    boolean test1 = pokerMain.cardDeck.size() != pokerMain.masterDeck.size();
    pokerMain.genDeck();
    boolean test2 = pokerMain.cardDeck.size() == pokerMain.masterDeck.size();
    return test1 && test2;
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
    while (!pokerMain.suitGuess())
      ;
  }

}