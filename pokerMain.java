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
  /**
   * Main Method used to initialize and finish the game
   */
  public static void main(String args[]) {
    int win = 0;
    Scanner scnr = new Scanner(System.in);
    String userIn;

    // User menu prompt
    System.out.println("Are you ready to ride the bus? (y/n)");
    System.out.println("Press h for help");
    // do/while loop that repeats prompts until user wins or presses q
    do {
      userIn = scnr.next().trim().toLowerCase();
      if (userIn.charAt(0) == 'y') {
        System.out.println("Shuffling cards...");
        shuffleCards();
        while (win == 0) {
          win = playGame(generateDeck());
        }

      } else if (userIn.charAt(0) == 'h') {
        System.out.println("===========Rules===========\nThis is a guessing game"
            + " that is played with one deck of cards and four stages\n1. Guess "
            + "the color of the card (red or black)\n2. Guess if the next card "
            + "is higher, lower, or the same compared to the card that was just "
            + "correctly guessed for color\n3. Guess if the next card is in between or"
            + " outside the value of the two previous cards\n4. Guess the suit of a drawn"
            + " card\nType exactly what the prompt asks for\nAnytime you are incorrect or user "
            + "input is bad the game will restart back at colors.\n\nGood Luck! "
            + "- Press y to play or q to quit");
        continue;
      } else if (userIn.charAt(0) == 'q') { // quits if the user enters q
        break;
      } else { // re-prompt the user if 'n' or incorrect input is entered
        System.out.println("Press y to play, q to quit, and h for instructions");
        continue;
      }

    }
    // complete will change to 1 if the user wins which will exit the loop
    while (userIn.charAt(0) != 'q' && win == 0);
    {
      System.out.println("\nThanks for playing!");
    }
    scnr.close();
  }

  /**
   * Helper method for try catch block used for delay timer to shuffle cards for
   * ~effect~
   */
  public static void shuffleCards() {
    try {
      TimeUnit.SECONDS.sleep(2); // shuffling time delay for effect
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is used to loop the game outside of the main method
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   * @return an integer that tells the main method whether the player won or not
   */
  public static int playGame(ArrayList<String> cardDeck) {
    int complete = 0;

    while (complete == 0) {
      if (cardDeck.size() == 0) { // checks if there are cards in the deck
        System.out.println("Reshuffling...");
        shuffleCards();
        cardDeck = generateDeck(); // creates a new full deck
      }
      complete = colorGuess(cardDeck); // starts the game with the color guess
    }
    return complete;
  }

  /**
   * Method used to remove a card at a desired index
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   * @param cardNum  the index of the card to be removed
   * @return the deck of cards minus the one that was removed
   */
  public static ArrayList<String> removeCard(ArrayList<String> cardDeck, int cardNum) {
    cardDeck.remove(cardNum);
    return cardDeck;
  }

  /**
   * Card generator method that randomly selects a card from the users sorted deck
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   * @return a random card from the card deck
   */
  public static int genCard(ArrayList<String> cardDeck) {
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
  public static int colorGuess(ArrayList<String> cardDeck) {
    Scanner scnr = new Scanner(System.in);
    int toggle = 0;
    int cardVal;

    while (toggle == 0) { // runs the color guess until the user wins or quits
      if (cardDeck.size() == 0) {
        System.out.println("Reshuffling...");
        shuffleCards();
        cardDeck = generateDeck(); // creates a new full deck
      }
      System.out.println("\nRed or Black?"); // Prompts the user
      String color = scnr.next().trim().toLowerCase();
      int cardNum = genCard(cardDeck); // randomly selects a card from user deck
      String drawnCard = cardDeck.get(cardNum); // gets the name of the card

      // if user guesses correctly when it is red
      if ((color.equals("red") && 
        (drawnCard.contains("Hearts") || drawnCard.contains("Diamonds")))) {
        cardVal = cardVal(drawnCard); // saves the numerical value of the card
        // removes the card that was correctly guessed
        cardDeck = removeCard(cardDeck, cardNum);
        // prints the card and praises user
        System.out.println(drawnCard);
        System.out.println("Good Work");
        highOrLow(cardDeck, cardVal);
        toggle = 1; // signal if user won
      }
      // if user guesses correctly when it is black
      else if ((color.equals("black") && 
        (drawnCard.contains("Spades") || drawnCard.contains("Clubs")))) {

        cardVal = cardVal(drawnCard); // saves the numerical value of the card
        // removes the card that was correctly guessed
        cardDeck = removeCard(cardDeck, cardNum);
        // prints the card and praises user
        System.out.println(drawnCard + "\nGood Work");
        // proceed to the next part of the game
        highOrLow(cardDeck, cardVal); 
        toggle = 1; // signal if user won
      }
      // if user is incorrect
      else {
        if (color.equals("black")) {
          // removes the incorrectly guessed card
          cardDeck = removeCard(cardDeck, cardNum);
          // prints the card and tells the user they are wrong
          System.out.println(drawnCard + "\nIncorrect, the card is red.");
        } else {
          cardDeck = removeCard(cardDeck, cardNum);
          System.out.println(drawnCard + "\nIncorrect, the card is black.");
        }
      }
    }
    scnr.close();

    return toggle;
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
  public static ArrayList<String> generateDeck() {
    String[] Suits = { "Hearts", "Clubs", "Spades", "Diamonds" };
    String[] Values = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
      "Jack", "Queen", "King" };
    int suitsLength = Suits.length;
    int valuesLength = Values.length;
    ArrayList<String> deck = new ArrayList<String>();

    // fills the card deck with each suit and value
    for (int i = 0; i < suitsLength; i++) {
      for (int j = 0; j < valuesLength; j++) {
        deck.add(Values[j] + " of " + Suits[i]);
      }
    }
    return deck;
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
  public static void highOrLow(ArrayList<String> cardDeck, int cardVal) {
    Scanner scnr = new Scanner(System.in);
    int toggle = 0; // 1 for correct -1 for incorrect
    int nextCardVal = 0;

    while (toggle == 0) {
      if (cardDeck.size() == 0) { // creates a new deck if the current one is empty
        System.out.println("Reshuffling...");
        shuffleCards();
        cardDeck = generateDeck();
      }
      System.out.println("\nHigher, Lower, or Same? (Aces low)"); // user prompt
      String guess = scnr.next().trim().toLowerCase();
      int nextCard = genCard(cardDeck); // randomly selects a card from user deck
      String nextCardString = cardDeck.get(nextCard); // gets the name of the card
      nextCardVal = cardVal(nextCardString); // gets the numeric value of the card
      System.out.println(nextCardString); // prints the card
      cardDeck = removeCard(cardDeck, nextCard); // removes the card from the deck

      // correct if the new card is higher than the previous and user input is higher
      if ((guess.contains("higher")) && (nextCardVal > cardVal)) {
        System.out.println("Sick!!");
        toggle = 1;
      }
      // correct if the new card is lower than the previous and user input is lower
      else if ((guess.contains("lower")) && (nextCardVal < cardVal)) {
        System.out.println("Sick!!");
        toggle = 1;
      }
      // incorrect if the cards are the same value
      else if ((guess.contains("same")) && nextCardVal == cardVal) {
        System.out.println("Well Played!!!");
        toggle = 1;
      } else {
        if (nextCardVal == cardVal) {
          System.out.println("You hate to see it");
        }
        System.out.println("\nBack to colors :/");
        toggle = -1;
        break;
      }
    }
    if (toggle == 1) {
      // advances the game
      inOrOut(cardDeck, nextCardVal, cardVal);
    } else if (toggle == -1) {
      playGame(cardDeck); // if incorrect the game is restarted
    }
    scnr.close();
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
  public static void inOrOut(ArrayList<String> cardDeck, int secondCardVal, int firstCardVal) {
    Scanner scnr = new Scanner(System.in);
    int toggle = 0;

    // Makes the secondCardVal always greater than cardVal
    // for easier comparison
    if (firstCardVal > secondCardVal) {
      int temp = secondCardVal;
      secondCardVal = firstCardVal;
      firstCardVal = temp;
    }

    while (toggle == 0) {
      if (cardDeck.size() == 0) {
        System.out.println("Reshuffling...");
        shuffleCards();
        cardDeck = generateDeck();
      }

      System.out.println("\nIn or Out?"); // User prompt

      String userIn = scnr.next().trim().toLowerCase();
      // retrieves the next card and saves its integer value
      int nextCard = genCard(cardDeck);
      String nextCardString = cardDeck.get(nextCard);
      int nextCardVal = cardVal(nextCardString);
      System.out.println(nextCardString); // print the next card
      cardDeck = removeCard(cardDeck, nextCard); // removes the card from the deck

      if (userIn.contains("in") && (nextCardVal < secondCardVal) && (nextCardVal > firstCardVal)) {
        System.out.println("Couldn't have done it better myself");
        toggle = 1; // if the user guesses correctly toggle signals 1
      } else if (userIn.contains("out") && 
          ((nextCardVal > secondCardVal) || (nextCardVal < firstCardVal))) {
        System.out.println("You know it");
        toggle = 1; // if the user guesses correctly toggle signals 1
      }
      // if the next card value is the same as either of the previous cards, restart
      else if ((nextCardVal == secondCardVal) || (nextCardVal == firstCardVal)) {
        System.out.println("Super unlucky...");
        toggle = -1; // indicates user was incorrect
        break;
      } else {
        System.out.println("You were so close!");
        toggle = -1;
        break;
      }
    }
    if (toggle == 1) {
      suitGuess(cardDeck); // goes to the final part of the game
    } else {
      playGame(cardDeck); // starts the game over
    }
    scnr.close();
  }

  /**
   * This method is used for the last challenge of the game which is guessing the
   * suit of a drawn card
   * 
   * @param cardDeck the continuous (sorted) deck of cards the user is playing
   *                 with
   */
  public static void suitGuess(ArrayList<String> cardDeck) {
    Scanner scnr = new Scanner(System.in);
    int toggle = 0;

    while (toggle == 0) {
      if (cardDeck.size() == 0) { // makes sure there are cards in the deck
        System.out.println("Reshuffling...");
        shuffleCards();
        cardDeck = generateDeck();
      }

      int nextCard = genCard(cardDeck);
      String nextCardString = cardDeck.get(nextCard);
      System.out.println("\nWhat Suit? (Clubs, Diamonds, Hearts, Spades)");
      String userIn = scnr.next().trim().toLowerCase();
      System.out.println(nextCardString); // prints the card after guess
      removeCard(cardDeck, nextCard); // removes the card from the deck

      if ((nextCardString.toLowerCase().contains(userIn))) {
        System.out.println("Congratulations, you win!");
        break;
      } else {
        System.out.println("So close! Back to the beginning.");
        toggle = -1; // signal for restart
        break;
      }
    }
    if (toggle == -1) {
      playGame(cardDeck); // restarts the game
    }
    scnr.close();
  }
}