// Created By Mark Truttmann
// Summer 2019
// Edited Winter 2020

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

/**
 * 
 * @author Mark Truttmann
 * 
 */
public class pokerMain {
  static ArrayList<String> masterDeck = new ArrayList<String>();
  static ArrayList<String> cardDeck = new ArrayList<String>();
  static Scanner scnr = new Scanner(System.in);
  static final String RULES = "\n=============== Rules ===============\nThis is a guessing game that is played with one deck of cards and four stages. Aces are low.\n1. Guess the color of the card (red or black)\n2. Guess if the next card is higher, lower, or the same compared to the previously drawn card\n3. Guess if the next card is in between or outside the value of the two previous cards\n4. Guess the suit of a drawn card\nFor each stage, read the prompt and type your guess from the choices in the brackets []\nAnytime you are incorrect the game will restart back at colors.\n\nGood Luck!";

  /**
   * Main Method used to initialize and finish the game
   */
  public static void main(String args[]) {
    // User menu prompt
    System.out.println("Are you ready to ride the bus?");
    String prompt = "Press y to play, q to quit, and e for the rules.";
    ArrayList<String> validInput = new ArrayList<String>();
    validInput.add("y");

    String userIn = inputLoop(validInput, prompt);
    if (userIn.equals("y")) {
      colorGuess();
    }
    System.out.println("\nThanks for playing!\n");
    scnr.close();
  }

  /**
   * This method is the first step of the game. It also acts as the driver as
   * anytime you guess incorrectly, you are returned here
   */
  public static void colorGuess() {
    boolean complete = false;

    // runs the game until the user wins or quits
    while (!complete) {
      checkDeck();
      String prompt = "\nRed or Black? [r/b]";
      ArrayList<String> validInput = new ArrayList<String>();
      validInput.add("r");
      validInput.add("b");

      String userIn = inputLoop(validInput, prompt);
      if (userIn.equals("q")) {
        break;
      }

      // gets name and numeric value of card
      String drawnCard = genCard();
      System.out.println("\n" + drawnCard);

      // compares user guess to red or black suits
      if (((userIn.equals("r") && (drawnCard.contains("Hearts") || drawnCard.contains("Diamonds"))))
          || ((userIn.equals("b") && (drawnCard.contains("Spades") || drawnCard.contains("Clubs"))))) {
        quip(true);
        complete = highOrLow(drawnCard);
      } else {
        System.out.println("Incorrect, the card is " + (userIn.equals("b") ? "red" : "black") + ".");
      }
    }
  }

  /**
   * This method is used for the second part of the game where the user guesses
   * whether or not the next card is higher, lower, or the same as the previous
   * 
   * @param cardVal numeric value of the previous card
   * @return boolean value signaling whether or not the game is complete
   */
  public static boolean highOrLow(String prevCard) {
    checkDeck();
    int prevCardVal = cardVal(prevCard);
    String prompt = "\nHigher, lower, or the Same? [h/l/s]\n-> " + prevCard;
    ArrayList<String> validInput = new ArrayList<String>();
    validInput.add("h");
    validInput.add("l");
    validInput.add("s");

    String userIn = inputLoop(validInput, prompt);
    if (userIn.equals("q")) {
      return true;
    }

    String nextCardString = genCard();
    int nextCardVal = cardVal(nextCardString);
    System.out.println("\n" + nextCardString);

    // compare user input to card values
    if (((userIn.equals("h")) && (nextCardVal > prevCardVal))
        || ((userIn.equals("l")) && (nextCardVal < prevCardVal))) {
      quip(true);
      return inOrOut(nextCardVal, prevCardVal);
    }
    // incorrect if the cards are the same value
    else if ((userIn.equals("s")) && nextCardVal == prevCardVal) {
      System.out.println("Calculated.\nWell Played.");
      return inOrOut(nextCardVal, prevCardVal);
    } else {
      if (nextCardVal == prevCardVal) { // same card is the worst...
        System.out.println("Gotcha!\nBack To colors");
        return false;
      }
      quip(false);
      return false;
    }
  }

  /**
   * This method is used for the third challenge of the game to see if the next
   * card is in between or outside the numeric values of the previous two
   * 
   * @param secondCardVal the int value of the second card drawn
   * @param firstCardVal  the int value of the first card drawn
   */
  public static boolean inOrOut(int secondCardVal, int firstCardVal) {
    checkDeck();
    // Makes the secondCardVal the upper bound for comparison
    if (firstCardVal > secondCardVal) {
      int temp = secondCardVal;
      secondCardVal = firstCardVal;
      firstCardVal = temp;
    }
    String prompt = "\nIn or out? [i/o]\n-> " + firstCardVal + " and " + secondCardVal;
    ArrayList<String> validInput = new ArrayList<String>();
    validInput.add("i");
    validInput.add("o");

    String userIn = inputLoop(validInput, prompt);
    if (userIn.equals("q")) {
      return true;
    }

    // retrieves the next card and saves its integer value
    String nextCardString = genCard();
    int nextCardVal = cardVal(nextCardString);
    System.out.println("\n" + nextCardString);

    // Compares user input to the next card and the bounds based off of the previous
    // two cards
    if ((userIn.equals("i") && (nextCardVal < secondCardVal) && (nextCardVal > firstCardVal))
        || (userIn.equals("o") && ((nextCardVal > secondCardVal) || (nextCardVal < firstCardVal)))) {
      quip(true);
      return suitGuess();
    }
    // if the next card value is the same as either of the previous cards, restart
    else if ((nextCardVal == secondCardVal) || (nextCardVal == firstCardVal)) {
      System.out.println("\nSuper unlucky...\nBack to colors!");
      return false;
    } else {
      quip(false);
      return false;
    }
  }

  /**
   * This method is the last challenge of the game: guessing the suit of a drawn
   * card
   * 
   * @return a boolean value signaling whether or not to complete the game
   */
  public static boolean suitGuess() {
    checkDeck();

    String prompt = "\nClubs, Diamonds, Hearts, or Spades? [c/d/h/s]";
    ArrayList<String> validInput = new ArrayList<String>();
    validInput.add("c");
    validInput.add("d");
    validInput.add("h");
    validInput.add("s");

    String userIn = inputLoop(validInput, prompt);
    if (userIn.equals("q")) {
      return true;
    }

    // draws a card
    String nextCardString = genCard();
    System.out.println("\n" + nextCardString);
    // split for user and suit comparison
    String[] splitCardString = nextCardString.split(" ");

    if ((splitCardString[2].toLowerCase().charAt(0) == userIn.charAt(0))) {
      System.out.println("Congratulations, you win!");
      while (!userIn.equals("y") && !userIn.equals("n")) {
        System.out.println("\nWould you like to play again? (y/n)");
        userIn = scnr.next().trim().toLowerCase();
      }
      if (userIn.equals("y")) {
        cardDeck.clear(); // must have new deck for restart
        System.out.print("\nRe-"); // Shuffling cards ;)
        genDeck(); // creates new deck
        return false;
      } else {
        return true;
      }
    } else {
      System.out.println("So close!\nBack to the beginning.");
      return false;
    }
  }

  /**
   * Helper method used to check if there are cards in the deck.
   */
  public static void checkDeck() {
    if (masterDeck.size() != 52) {
      masterDeck.clear();
      initMasterDeck();
    }
    if (cardDeck.isEmpty()) {
      System.out.println("\nCards in deck: [0]");
      System.out.print("Re-");
      genDeck();
    }
  }

  /**
   * Helper method used to loop a prompt until correct input is entered
   */
  public static String inputLoop(ArrayList<String> validInput, String prompt) {
    String userIn = "";
    validInput.add("q");

    while (!validInput.contains(userIn)) {
      if (userIn.equals("e")) {
        System.out.println(RULES);
      }
      System.out.println(prompt);
      userIn = scnr.next().trim().toLowerCase();
    }
    return userIn;
  }

  /**
   * Card generator method that randomly selects and removes a card from the
   * sorted deck
   *
   * @return the name of a card from the deck
   */
  public static String genCard() {
    int cardIndex = new Random().nextInt(cardDeck.size());
    String cardName = cardDeck.get(cardIndex);
    cardDeck.remove(cardIndex);
    return cardName;
  }

  /**
   * Helper method used to determine the numerical value of a selected card
   * 
   * @param card the name of the card drawn
   * @return the int value of the card
   */
  public static int cardVal(String card) {
    // checks to see if the card is a face card or 10
    if (card.contains("Ace")) {
      return 1;
    } else if (card.contains("Jack")) {
      return 11;
    } else if (card.contains("Queen")) {
      return 12;
    } else if (card.contains("King")) {
      return 13;
    } else if (card.contains("10")) {
      return 10;
    } else {
      // otherwise takes the first character of the card (always a number)
      return Character.getNumericValue((char) card.charAt(0));
    }
  }

  /**
   * Creates a master deck to be copied so values and suits only need to be
   * generated once
   */
  public static void initMasterDeck() {
    String[] Suits = { "Hearts", "Clubs", "Spades", "Diamonds" };
    String[] Values = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
    // fills the card deck with each suit and value
    for (int i = 0; i < Suits.length; i++) {
      for (int j = 0; j < Values.length; j++) {
        masterDeck.add(Values[j] + " of " + Suits[i]);
      }
    }
    genDeck();
  }

  /**
   * Generates a sorted deck from masterDeck to cardDeck with timer delay for
   * ~effect~
   */
  public static void genDeck() {
    System.out.println("Shuffling cards...");
    if (!cardDeck.isEmpty()) {
      cardDeck.clear();
    }
    for (String c : masterDeck) {
      cardDeck.add(new String(c));
    }
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method used to generate and print random user feedback
   * 
   * @param positive true/false based on if user completed their previous
   */
  public static void quip(boolean positive) {
    String[] win = { "Nice!!\nOn to the next one.", "Well done.\nNext!", "Good work!", "Calculated.", "Well played.",
        "Couldn't have done it better myself." };
    String[] lose = { "That's tough.", "You hate to see it.", "Better luck next time!", "Savage!!\nBack to colors.",
        "So close!" };
    if (positive) {
      System.out.println(win[new Random().nextInt(win.length)]);
    } else {
      System.out.println(lose[new Random().nextInt(lose.length)]);
    }
  }
}