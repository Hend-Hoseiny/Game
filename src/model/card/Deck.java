package model.card;

import engine.GameManager;
import engine.board.BoardManager;
import model.card.standard.*;
import model.card.wild.*;

import java.util.*;
import java.io.*;

public class Deck {
    private static final String CARDS_FILE = "Cards.csv";
    private static ArrayList<Card> cardsPool;

    public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException {
        cardsPool = new ArrayList<Card>();
        Scanner lineReader = new Scanner(new File(CARDS_FILE));
        while (lineReader.hasNext()) {
            String row = lineReader.nextLine();
            Scanner sc = new Scanner(row);
            sc.useDelimiter(",");

            int code = sc.nextInt();
            int frequency = sc.nextInt();
            String name = sc.next();
            String description = sc.next();
            int rank = -1;
            String suit = "";
            if (code == 14 || code == 15) {
                while(sc.hasNext()){
                    description+=","+sc.next();
                }
                if(description.charAt(0)=='\"')
                    description = description.substring(1);
                if(description.charAt(description.length()-1)=='\"')
                    description = description.substring(0, description.length() - 1);
                while(description.charAt(description.length()-1)==',')
                    description = description.substring(0, description.length() - 1);
            } else {
                rank = sc.nextInt();
                suit = sc.next();
            }
            switch (code) {
                case 0:
                    for (int i = 0; i < frequency; i++) {
                        Standard currentCard = new Standard(name, description, rank, Suit.valueOf(suit), boardManager,gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 1:
                    for (int i = 0; i < frequency; i++) {
                        Ace currentCard = new Ace(name, description, Suit.valueOf(suit), boardManager, gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 4:
                    for (int i = 0; i < frequency; i++) {
                        Four currentCard = new Four(name, description, Suit.valueOf(suit), boardManager ,gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 5:
                    for (int i = 0; i < frequency; i++) {
                        Five currentCard = new Five(name, description, Suit.valueOf(suit), boardManager,gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 7:
                    for (int i = 0; i < frequency; i++) {
                        Seven currentCard = new Seven(name, description, Suit.valueOf(suit), boardManager,gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 10:
                    for (int i = 0; i < frequency; i++) {
                        Ten currentCard = new Ten(name, description, Suit.valueOf(suit), boardManager,gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 11:
                    for (int i = 0; i < frequency; i++) {
                        Jack currentCard = new Jack(name, description, Suit.valueOf(suit), boardManager, gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 12:
                    for (int i = 0; i < frequency; i++) {
                        Queen currentCard = new Queen(name, description, Suit.valueOf(suit), boardManager, gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 13:
                    for (int i = 0; i < frequency; i++) {
                        King currentCard = new King(name, description, Suit.valueOf(suit), boardManager, gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 14:
                    for (int i = 0; i < frequency; i++) {
                        Burner currentCard = new Burner(name, description, boardManager, gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
                case 15:
                    for (int i = 0; i < frequency; i++) {
                        Saver currentCard = new Saver(name, description, boardManager, gameManager);
                        cardsPool.add(currentCard);
                    }
                    break;
            }
            sc.close();
        }
        lineReader.close();
    }

    public static ArrayList<Card> drawCards(){
        Collections.shuffle(cardsPool);
        ArrayList<Card> result=new ArrayList<Card>();
        for(int i = 0;i<4;i++)
            result.add(cardsPool.remove(0));
        return result;
    }    

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void refillPool(ArrayList<Card> cards){
        cardsPool.addAll(cards);
    }

    public static int getPoolSize(){
        return cardsPool.size();
    }

    public static ArrayList<Card> getDeck(){
        return cardsPool;
    }

    public static void main(String[] args) throws IOException {
        Deck.loadCardPool(null, null);
        for(int i=0; i<cardsPool.size(); i++){
            System.out.println(cardsPool.get(i).getDescription());
        }
    }

}

