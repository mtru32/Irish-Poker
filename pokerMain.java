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
  static ArrayList<String> cardDeck = new ArrayList<String>();
  static Scanner scnr = new Scanner(System.in);
  /**
   * Main Method used to initialize and finish the game
   */
  public static void main(String args[]) {
    int complete = 0;

    // User menu prompt
    System.out.println("Are you ready to ride the bus? (y/n)");
    System.out.println("Press h for help");
    String userIn = scnr.next().trim().toLowerCase();
    // do/while loop that repeats prompts until user wins or presses q
    while (userIn.charAt(0) != 'q' && complete == 0){
      if (userIn.charAt(0) == 'y') {
        generateDeck();
        complete = playGame();
      } else if (userIn.charAt(0) == 'h') {
        System.out.println("\n===========Rules===========\nThis is a guessing game"
            + " that is played with one deck of cards and four stages\n1. Guess "
            + "the color of the card (red or black)\n2. Guess if the next card "
            + "is higher, lower, or the same compared to the card that was just "
            + "correctly guessed for color\n3. Guess if the next card is in between or"
            + " outside the value of the two previous cards\n4. Guess the suit of a drawn"
            + " card\nType exactly what the prompt asks for\nAnytime you are incorrect or user "
            + "input is bad the game will restart back at colors.\n\nGood Luck! "
            + "- Press y to play or q to quit");
            userIn = scnr.next().trim().toLowerCase();
      } else if (userIn.charAt(0) == 'q') {
        break;
      } else { // re-prompt the user if 'n' or incorrect input is entered
        System.out.println("Press y to play, q to quit, and h for help");
        userIn = scnr.next().trim().toLowerCase();
      }
    }
    System.out.println("\nThanks for playing!");
    scnr.close();
  }

  /**
   * Helper method used to check if there are cards in the deck. Try catch 
   * block timer used for ~effect~
   */
  public static void checkDeck() {
    if (cardDeck.isEmpty()) {
      System.out.println("Deck out of cards.");
      generateDeck();
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * This method is used to loop the game outside of the main method
   * 
   * @return an integer that tells the main method whether the player won or not
   */
  public static int playGame() {
    int complete = 0;

    while (complete == 0) {
      checkDeck();
      complete = colorGuess(); // starts the game with the color guess
      System.out.println("COMPLETE: " + complete);
    }
    return complete;
  }

  /**
   * Card generator method that randomly selects a card from the users sorted deck
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   * @return a random card from the card deck
   */
  public static int genCard() {
    int cardNum = new Random().nextInt(cardDeck.size());
    return cardNum;
  }

  /**
   * colorGuess method uses user input to see if the correct color is guessed 
   * on a random card
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   * @return 1 if the user wins, 0 otherwise
   */
  public static int colorGuess() {
    int complete = 0;
    int cardVal;    

    // runs the color guess until the user wins or quits
    while (complete == 0) {
      checkDeck();
      String userIn = "";

      while (!userIn.equals("red") && !userIn.equals("black") && !userIn.equals("q")) {
        System.out.println("\nRed or Black?"); // Prompts the user
        userIn = scnr.next().trim().toLowerCase();
      }
      if (userIn.equals("q")) {
        return -1;
      }

      int cardNum = genCard(); // randomly selects a card from user deck
      String drawnCard = cardDeck.get(cardNum); // gets the name of the card
      cardDeck.remove(cardNum);
      cardVal = cardVal(drawnCard);

      // if user guesses correctly when it is red
      if ((userIn.equals("red") && 
        (drawnCard.contains("Hearts") || drawnCard.contains("Diamonds")))) {
        System.out.println(drawnCard + "\nGood Work");
        complete = highOrLow(cardVal);
      }
      // if user guesses correctly when it is black
      else if ((userIn.equals("black") && 
        (drawnCard.contains("Spades") || drawnCard.contains("Clubs")))) {
        cardVal = cardVal(drawnCard); // saves the numerical value of the card
        System.out.println(drawnCard + "\nGood Work");
        // proceed to the next part of the game
        complete = highOrLow(cardVal); 
      }
      // if user is incorrect
      else {
        if (userIn.equals("black")) {
          System.out.println(drawnCard + "\nIncorrect, the card is red.");
        } else {
          System.out.println(drawnCard + "\nIncorrect, the card is black.");
        }
      }
    }
    return complete;
  }

  /**
   * Helper method used to determine the numerical value of a selected card
   * 
   * @param card the name of the card drawn
   * @return the numeric value of the card
   */
  public static int cardVal(String card) {
    int cardValue = 0;

    // checks to see if the card is a face card or 10
    if (card.contains("Ace")) {
      cardValue = 1;
    } else if (card.contains("Jack")) {
      cardValue = 11;
    } else if (card.contains("Queen")) {
      cardValue = 12;
    } else if (card.contains("King")) {
      cardValue = 13;
    } else if (card.contains("10")) {
      cardValue = 10;
    } else { // otherwise takes the first character of the card (always a number)
      char number = card.charAt(0);
      cardValue = Character.getNumericValue(number);
    }
    return cardValue;
  }

  /**
   * Generates a sorted deck for the user to play with
   * 
   * @return the sorted deck
   */
  public static void generateDeck() {
    System.out.println("Shuffling Cards...");
    String[] Suits = { "Hearts", "Clubs", "Spades", "Diamonds" };
    String[] Values = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
      "Jack", "Queen", "King" };
    int suitsLength = Suits.length;
    int valuesLength = Values.length;

    // fills the card deck with each suit and value
    for (int i = 0; i < suitsLength; i++) {
      for (int j = 0; j < valuesLength; j++) {
        cardDeck.add(Values[j] + " of " + Suits[i]);
      }
    }
    //return deck;
  }

  /**
   * This method is used for the second part of the game where the user guesses
   * whether or not the next card is higher, lower, or the same than their
   * previous
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   * @param cardVal  numeric value of the previous card
   */
  public static int highOrLow(int cardVal) {
    int nextCardVal = 0;

    checkDeck();
    System.out.println("\nHigher, Lower, or Same? (Aces low)"); // user prompt
    String guess = scnr.next().trim().toLowerCase();
    int nextCard = genCard(); // randomly selects a card from user deck
    String nextCardString = cardDeck.get(nextCard); // gets the name of the card
    nextCardVal = cardVal(nextCardString); // gets the numeric value of the card
    System.out.println(nextCardString);
    cardDeck.remove(nextCard);

    // correct if the new card is higher than the previous and user input is higher
    if ((guess.contains("higher")) && (nextCardVal > cardVal)) {
      System.out.println("Sick!!");
      return inOrOut(nextCardVal, cardVal);
    }
    // correct if the new card is lower than the previous and user input is lower
    else if ((guess.contains("lower")) && (nextCardVal < cardVal)) {
      System.out.println("Sick!!");
      return inOrOut(nextCardVal, cardVal);
    }
    // incorrect if the cards are the same value
    else if ((guess.contains("same")) && nextCardVal == cardVal) {
      System.out.println("Well Played!!!");
      return inOrOut(nextCardVal, cardVal);
    } else {
      if (nextCardVal == cardVal) {
        System.out.println("You hate to see it");
      }
      System.out.println("\nBack to colors :/");
      return 0;
    }
  }

  /**
   * 
   * This method is used for the third challenge of the game to see if the next
   * card is in between the numeric values of the previous two, or if it is
   * outside
   * 
   * @param cardDeck      cardDeck the continuous (sorted) deck of cards the user
   *                      is playing with
   * @param secondCardVal the int value of the second card drawn
   * @param firstCardVal  the int value of the first card drawn
   */
  public static int inOrOut(int secondCardVal, int firstCardVal) {
    // Makes the secondCardVal always greater than cardVal for comparison
    if (firstCardVal > secondCardVal) {
      int temp = secondCardVal;
      secondCardVal = firstCardVal;
      firstCardVal = temp;
    }

    checkDeck();

    System.out.println("\nIn or Out?"); // User prompt

    String userIn = scnr.next().trim().toLowerCase();
    // retrieves the next card and saves its integer value
    int nextCard = genCard();
    String nextCardString = cardDeck.get(nextCard);
    int nextCardVal = cardVal(nextCardString);
    System.out.println(nextCardString); // print the next card
    cardDeck.remove(nextCard);

    if (userIn.contains("in") && (nextCardVal < secondCardVal) && (nextCardVal > firstCardVal)) {
      System.out.println("Couldn't have done it better myself");
      return suitGuess();
    } else if (userIn.contains("out") && 
        ((nextCardVal > secondCardVal) || (nextCardVal < firstCardVal))) {
      System.out.println("You know it");
      return suitGuess();
    }
    // if the next card value is the same as either of the previous cards, restart
    else if ((nextCardVal == secondCardVal) || (nextCardVal == firstCardVal)) {
      System.out.println("Super unlucky...");
      return 0;
    } else {
      System.out.println("You were so close!");
      return 0;
    }
  }

  /**
   * This method is used for the last challenge of the game which is guessing the
   * suit of a drawn card
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   */
  public static int suitGuess() {
    checkDeck();

    int nextCard = genCard();
    String nextCardString = cardDeck.get(nextCard);
    System.out.println("\nWhat Suit? (Clubs, Diamonds, Hearts, Spades)");
    String userIn = scnr.next().trim().toLowerCase();
    System.out.println(nextCardString);
    cardDeck.remove(nextCard);

    if ((nextCardString.toLowerCase().contains(userIn))) {
      System.out.println("Congratulations, you win!");
      return 1;
    } else {
      System.out.println("So close! Back to the beginning.");
      return 0;
    }
  }
}