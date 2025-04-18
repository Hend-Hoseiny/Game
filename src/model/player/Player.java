package model.player;

import java.util.ArrayList;

import exception.InvalidCardException;
import model.Colour;
import model.card.*;
import model.card.standard.Standard;
import model.card.standard.Suit;

public class Player {
    private final String name;
    private final Colour colour;
    private ArrayList<Card> hand;
    private final ArrayList<Marble> marbles; //represents home zone
    private Card selectedCard;
    private final ArrayList<Marble> selectedMarbles;

    public Player(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
        this.hand = new ArrayList<Card>();
        this.selectedMarbles = new ArrayList<Marble>();
        this.marbles = new ArrayList<Marble>();
        
        for (int i = 0; i < 4; i++) 
            this.marbles.add(new Marble(colour));
        
        this.selectedCard = null;
    }

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Marble> getMarbles() {
        return marbles;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void regainMarble(Marble marble){
        marbles.add(marble);        
    }

    public Marble getOneMarble(){
        if(marbles.isEmpty())
            return null;
        return marbles.get(0);
    }

    public void selectCard(Card card) throws InvalidCardException{
        if(hand.contains(card))
            selectedCard=card;
        else
            throw new InvalidCardException("The card does not belong to the player's hand.");
    }

    public static void main(String[] args) throws InvalidCardException {
        Player x = new Player("ali" , Colour.BLUE);
        for(int i=0 ; i<x.marbles.size(); i++)
            System.out.println(x.marbles.get(i).getColour());
        // x.selectCard(new Standard("ace", "bla", 0, Suit.CLUB, null, null));
        int y = 0;
        System.out.println(5/y);
    }





}
