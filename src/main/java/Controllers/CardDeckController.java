package Controllers;

import Models.Actions;
import Models.Card;
import Models.CardDeck;
import Models.Player;
import Monopoly.UUID;

import java.util.function.Consumer;

// check commit msg for reasoning for changes
public class CardDeckController implements Controller {

    private static CardDeckController cardController;
    private CardDeck cardDeck;

    private final static UUID CHANCE_JAIL_CARD_UUID = new UUID("CARD-8");
    private final static UUID COMMUNITY_JAIL_CARD_UUID = new UUID("CARD-20");

    public CardDeckController() {
        cardDeck = new CardDeck();
    }

// TODO:
//  1. Add Actions.
    public void setCardDecks() {

        Card c1 = new Card(new UUID("CARD-1"), "Advance to Boardwalk", Actions::exampleAction);
        Card c2 = new Card(new UUID("CARD-2"), "Advance to go (collect $200)", Actions::exampleAction);
        Card c3 = new Card(new UUID("CARD-3"), "Advance to Illinois Avenue. If you pass go, collect $200", Actions::exampleAction);
        Card c4 = new Card(new UUID("CARD-4"), "Advance to St. Charles Place. If you pass go, collect $200", Actions::exampleAction);
        Card c5 = new Card(new UUID("CARD-5"), "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled.", Actions::exampleAction);
        Card c6 = new Card(new UUID("CARD-6"), "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total of ten times amount thrown.", Actions::exampleAction);
        Card c7 = new Card(new UUID("CARD-7"), "Bank pays you dividend of $50", Actions::exampleAction);
        Card c8 = new Card(CardDeckController.CHANCE_JAIL_CARD_UUID, "Get out of jail free", Actions::exampleAction);
        Card c9 = new Card(new UUID("CARD-9"), "Go back 3 spaces", Actions::exampleAction);
        Card c10 = new Card(new UUID("CARD-10"), "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200", Actions::exampleAction);
        Card c11 = new Card(new UUID("CARD-11"), "Make general repairs on all your property. For each house pay $25. For each hotel pay $100", Actions::exampleAction);
        Card c12 = new Card(new UUID("CARD-12"), "Speeding fine $15", Actions::exampleAction);
        Card c13 = new Card(new UUID("CARD-13"), "Take a trip to Reading Railroad. If you pass Go, collect $200", Actions::exampleAction);
        Card c14 = new Card(new UUID("CARD-14"), "You have been elected Chairman of the Board. Pay each player $50", Actions::exampleAction);
        Card c15 = new Card(new UUID("CARD-15"), "Your building loan matures. Collect $150", Actions::exampleAction);

        Card c16 = new Card(new UUID("CARD-16"), "Advance to Go (Collect $200)", Actions::exampleAction);
        Card c17 = new Card(new UUID("CARD-17"), "Bank error in your favor. Collect $200", Actions::exampleAction);
        Card c18 = new Card(new UUID("CARD-18"), "Doctor’s fee. Pay $50", Actions::exampleAction);
        Card c19 = new Card(new UUID("CARD-19"), "From sale of stock you get $50", Actions::exampleAction);
        Card c20 = new Card(CardDeckController.COMMUNITY_JAIL_CARD_UUID, "Get Out of Jail Free", Actions::exampleAction);
        Card c21 = new Card(new UUID("CARD-21"), "Go to Jail. Go directly to jail, do not pass Go, do not collect $200", Actions::exampleAction);
        Card c22 = new Card(new UUID("CARD-22"), "Holiday fund matures. Receive $100", Actions::exampleAction);
        Card c23 = new Card(new UUID("CARD-23"), "Income tax refund. Collect $20", Actions::exampleAction);
        Card c24 = new Card(new UUID("CARD-24"), "It is your birthday. Collect $10 from every player", Actions::exampleAction);
        Card c25 = new Card(new UUID("CARD-25"), "Life insurance matures. Collect $100", Actions::exampleAction);
        Card c26 = new Card(new UUID("CARD-26"), "Pay hospital fees of $100", Actions::exampleAction);
        Card c27 = new Card(new UUID("CARD-27"), "Pay school fees of $50", Actions::exampleAction);
        Card c28 = new Card(new UUID("CARD-28"), "Receive $25 consultancy fee", Actions::exampleAction);
        Card c29 = new Card(new UUID("CARD-29"), "You are assessed for street repair. $40 per house. $115 per hotel", Actions::exampleAction);
        Card c30 = new Card(new UUID("CARD-30"), "You have won second prize in a beauty contest. Collect $10", Actions::exampleAction);
        Card c31 = new Card(new UUID("CARD-31"), "You inherit $100", Actions::exampleAction);
    }

    //TODO:
    // 1. Create the card decks for both community chest and chance - function gets called when game gets created
    // 2. Upload the array of the decks that are shuffled to firebase
    // 3. when a player graps a card, card gets taken from index 0 and returns to the back of the deck.


    /*@Override
    public void registerObserver(View v) {
        cardDeck.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        cardDeck.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        cardDeck.notifyObservers(ds);
    }*/
}
