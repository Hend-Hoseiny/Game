package model.card;

import engine.GameManager;
import engine.board.BoardManager;
import java.util.*;
import java.io.*;

public class Deck {
    private static final String CARDS_FILE = "Cards.csv";
    private static ArrayList<Card> cardsPool = new ArrayList<Card>();

    public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException{
        Scanner sc = new Scanner(new File(CARDS_FILE));
        while(sc.hasNext()){
            String row = sc.nextLine();

            Scanner lineReader = new Scanner(row);
            lineReader.useDelimiter(",");

            int code = lineReader.nextInt();
            int frequency = lineReader.nextInt();
            String name = lineReader.next();
            String description = lineReader.next();

            if(code==14 || code==15){
                description+=lineReader.next()+lineReader.next();
                lineReader.next();
                description=description.substring(1);
                description=description.substring(0,description.length()-1);
                System.out.println("code= " + code + " frequency= " + frequency + " name= " + name + " description= " + description );
            }
            else{
                int rank = lineReader.nextInt();
                String suit = lineReader.next();
                System.out.println("code= " + code + " frequency= " + frequency + " name= " + name + " description= " + description + " rank= " + rank + " suit= " + suit );
            }        
            lineReader.close();
        }
        sc.close();
    }
    public static ArrayList<Card> drawCards(){
        Collections.shuffle(cardsPool);
        ArrayList<Card> result = new ArrayList<Card>();
        for(int i=0 ; i<4 ; i++)
            result.add(cardsPool.remove(0));
        return result;
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File(CARDS_FILE));
        while(sc.hasNext()){
            String row = sc.nextLine();

            Scanner lineReader = new Scanner(row);
            lineReader.useDelimiter(",");

            int code = lineReader.nextInt();
            int frequency = lineReader.nextInt();
            String name = lineReader.next();
            String description = lineReader.next();

            if(code==14 || code==15){
                description+=lineReader.next()+lineReader.next();
                lineReader.next();
                description=description.substring(1);
                description=description.substring(0,description.length()-1);
                System.out.println("code= " + code + " frequency= " + frequency + " name= " + name + " description= " + description );
            }
            else{
                int rank = lineReader.nextInt();
                String suit = lineReader.next();
                System.out.println("code= " + code + " frequency= " + frequency + " name= " + name + " description= " + description + " rank= " + rank + " suit= " + suit );
            }        
            lineReader.close();
        }
        sc.close();
    }
}