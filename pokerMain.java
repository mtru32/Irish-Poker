// Created By Mark Truttmann
// May, Summer 2019

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class pokerMain {
    public static void main(String args[]) {
        int win = 0;
        int complete = 0;
        Scanner scnr = new Scanner(System.in);
        String userIn;


        System.out.println("Are you ready to ride the bus? (y/n)");
        do {
            userIn = scnr.next().trim();
            if (userIn.charAt(0) == 'y' || userIn.charAt(0) == 'Y') {
                System.out.println("Shuffling cards...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                do {
                    win = playGame(generateDeck());
                }
                while (win == 0); {
                    complete = 1;
                }

            }
            else if (userIn.charAt(0) == 'q' || userIn.charAt(0) == 'Q') {
                break;
            }
            else {
                System.out.println("Press y to play, and q to quit");
                continue;
            }

        }
        while (userIn.charAt(0) != 'q' && complete == 0); {
            System.out.println(" ");                                // Added in for output spacing
            System.out.println("Thanks for playing!");
        }
        scnr.close();
    }


    //Game Player
    public static int playGame(ArrayList <String> cardDeck) {
        int complete = 0;

        while(complete == 0) {
            if (cardDeck.size() == 0) {
                System.out.println("Reshuffling...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cardDeck = generateDeck();
            }
            complete = colorGuess(cardDeck);            
        }
        return complete;
    }


    //Make Card Remover Method
    public static ArrayList<String> removeCard(ArrayList<String> cardDeck, int cardNum){
        cardDeck.remove(cardNum);
        return cardDeck;
    }


    //Card Generator Method
    public static int genCard(ArrayList<String> cardDeck) {
        int cardNum = new Random().nextInt(cardDeck.size());
        return cardNum;
    }



    // Make Red or Black Method
    public static int colorGuess(ArrayList<String> cardDeck) {
        Scanner scnr = new Scanner(System.in);
        int toggle = 0;

        while (toggle == 0) {
            if (cardDeck.size() == 0) {
                System.out.println("Reshuffling...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cardDeck = generateDeck();
            }
            System.out.println(" ");                // Added for output spacing
            System.out.println("Red or Black?");
            String color = scnr.next().trim().toLowerCase();
            int cardNum = genCard(cardDeck);
            String drawnCard = cardDeck.get(cardNum);
            int cardVal;

            if ((color.equals("red") && drawnCard.contains("Hearts")) || ((color.equals("red") && drawnCard.contains("Diamonds")))) {
                cardVal = cardVal(drawnCard);
                cardDeck = removeCard(cardDeck, cardNum);
                System.out.println(drawnCard);
                System.out.println("Good Work");
                highOrLow(cardDeck, cardVal);
                toggle = 1;

            }
            else if ((color.equals("black") && drawnCard.contains("Spades")) || (color.equals("black") && drawnCard.contains("Clubs"))){
                cardVal = cardVal(drawnCard);
                cardDeck = removeCard(cardDeck, cardNum);
                System.out.println(drawnCard);
                System.out.println("Good Work");
                highOrLow(cardDeck, cardVal);
                toggle = 1;
            }
            else {
                if (color.equals("black")) {
                    cardDeck = removeCard(cardDeck, cardNum);
                    System.out.println(drawnCard);
                    System.out.println("Incorrect, the card is red.");
                }
                else if (color.equals("red")) {
                    cardDeck = removeCard(cardDeck, cardNum);
                    System.out.println(drawnCard);
                    System.out.println("Incorrect, the card is black.");
                }
            }
        }
        scnr.close();
        return toggle;
    }


    //Card Values
    public static int cardVal(String card) {
        int cardValue = 0;

        if (card.contains("Ace")) {
            cardValue = 1;
        }
        else if (card.contains("Jack")) {
            cardValue = 11;
        }
        else if (card.contains("Queen")) {
            cardValue = 12;
        }
        else if (card.contains("King")) {
            cardValue = 13;
        }
        else if (card.contains("10")) {
            cardValue = 10;
        }
        else {
            char number = card.charAt(0);
            cardValue = Character.getNumericValue(number);    
        }
        return cardValue;
    }

    //Generate Deck
    public static ArrayList<String> generateDeck() {
        String[] Suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
        String[] Values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        int suitsLength = Suits.length;
        int valuesLength = Values.length;
        ArrayList<String> deck = new ArrayList<String>();

        for (int i = 0; i < suitsLength; i++) {
            for (int j = 0; j < valuesLength; j++) {
                deck.add(Values[j] + " of " + Suits[i]);
            }
        }
        return deck;
    }

    //Make Higher or Lower Method
    public static void highOrLow(ArrayList<String> cardDeck, int cardVal) {
        Scanner scnr = new Scanner(System.in);
        System.out.println(" ");                // Added in for output spacing
        int toggle = 0;
        int nextCardVal = 0;

        while (toggle == 0) {
            if (cardDeck.size() == 0) {
                System.out.println("Reshuffling...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cardDeck = generateDeck();
            }
            System.out.println("Higher or Lower?");
            String guess = scnr.next().trim().toLowerCase();
            int nextCard = genCard(cardDeck);
            String nextCardString = cardDeck.get(nextCard);
            nextCardVal = cardVal(nextCardString);
            System.out.println(nextCardString);
            cardDeck = removeCard(cardDeck, nextCard);

            if ((guess.contains("higher")) && (nextCardVal > cardVal)) {
                System.out.println("Sick!!");
                toggle = 1;
            }
            else if ((guess.contains("lower")) && (nextCardVal < cardVal)) {
                System.out.println("Sick!!");
                toggle = 1;
            }
            else if (nextCardVal == cardVal) {
                System.out.println("You hate to see it");
                toggle = -1;       
                break;
            }
            else {
                System.out.println(" ");                // Added for output spacing
                System.out.println("Back to colors :/");
                toggle = -1;
                break;
            }   
        }
        if (toggle == 1) {
            inOrOut(cardDeck, nextCardVal, cardVal);
        }
        else if (toggle == -1) {
            playGame(cardDeck);
        }
        scnr.close();
    }

    //Make In or Out Method
    public static void inOrOut(ArrayList <String> cardDeck, int secondCardVal, int firstCardVal) {
        Scanner scnr = new Scanner(System.in);
        int toggle = 0;
        if (firstCardVal > secondCardVal) {              // Makes the secondCardVal always greater than cardVal
            int temp = secondCardVal;
            secondCardVal = firstCardVal;
            firstCardVal = temp;
        }

        while (toggle == 0) {
            if (cardDeck.size() == 0) {
                System.out.println("Reshuffling...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cardDeck = generateDeck();
            }

            System.out.println(" ");                // Added for output spacing
            System.out.println("In or Out?");

            String userIn = scnr.next().trim().toLowerCase();
            int nextCard = genCard(cardDeck);
            String nextCardString = cardDeck.get(nextCard);
            int nextCardVal = cardVal(nextCardString);
            System.out.println(nextCardString);
            cardDeck = removeCard(cardDeck, nextCard);

            if (userIn.contains("in") && (nextCardVal < secondCardVal) && (nextCardVal > firstCardVal)) {
                System.out.println("Couldn't have done it better myself");
                toggle = 1;
            }
            else if (userIn.contains("out") && ((nextCardVal > secondCardVal) || (nextCardVal < firstCardVal))) {
                System.out.println("You know it");
                toggle = 1;
            }
            else if ((nextCardVal == secondCardVal) || (nextCardVal == firstCardVal)) {
                System.out.println("You hate to see it :/");
                toggle = -1;
                break;
            }
            else { 
                System.out.println("You were so close!");
                toggle = -1;
                break;
            }
        }
        if (toggle == 1) {
            suitGuess(cardDeck);
        }
        else if (toggle == -1) {
            playGame(cardDeck);
        }
        scnr.close();
    }

    //Make Suit Method
    public static void suitGuess(ArrayList <String> cardDeck) {
        Scanner scnr = new Scanner(System.in);
        int toggle = 0;

        while (toggle == 0) {
            if (cardDeck.size() == 0) {
                System.out.println("Reshuffling...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cardDeck = generateDeck();
            }

            System.out.println(" ");                // Added for output spacing
            System.out.println("What Suit? (Clubs, Diamonds, Hearts, Spades)");
            String userIn = scnr.next().trim().toLowerCase();
            int nextCard = genCard(cardDeck);
            String nextCardString = cardDeck.get(nextCard);
            removeCard(cardDeck, nextCard);

            if ((userIn.contains("clubs") && nextCardString.contains("clubs"))) {
                System.out.println("Congratulations, you win!");
                toggle = 1;
            }
            else if ((userIn.contains("diamonds") && nextCardString.contains("diamonds"))) {
                System.out.println("Congratulations, you win!");
                toggle = 1;
            }
            else if ((userIn.contains("hearts") && nextCardString.contains("hearts"))) {
                System.out.println("Congratulations, you win!");
                toggle = 1;
            }
            else if ((userIn.contains("spades") && nextCardString.contains("spades"))) {
                System.out.println("Congratulations, you win!");
                toggle = 1;
            }
            else {
                System.out.println("Way to pull a Noah");
                toggle = -1;
                break;
            }
        }
        if (toggle == -1) {
            playGame(cardDeck);
        }
        scnr.close();
    }

}