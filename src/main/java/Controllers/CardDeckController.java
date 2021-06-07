package Controllers;

import Models.Card;
import Models.CardDeck;
import Models.Wallet;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.function.Consumer;

public class CardDeckController implements Controller {

    private static CardDeckController cardController;
    private CardDeck cardDeck;

    private CardDeckController() {
        cardDeck = new CardDeck();
    }

    public static CardDeckController getInstance() {
        if(cardController == null) {
            cardController = new CardDeckController();
        }
        return cardController;
    }


// TODO:
//  1. Add Actions.
    public void setCardDecks(){
        Card c1 = new Card(Card.cardType.CHANCE, "Advance to Boardwalk", null);
        Card c2 = new Card(Card.cardType.CHANCE, "Advance to go (collect $200)", null);
        Card c3 = new Card(Card.cardType.CHANCE, "Advance to Illinois Avenue. If you pass go, collect $200", null);
        Card c4 = new Card(Card.cardType.CHANCE, "Advance to St. Charles Place. If you pass go, collect $200", null);
        Card c5 = new Card(Card.cardType.CHANCE, "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled.", null);
        Card c6 = new Card(Card.cardType.CHANCE, "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total of ten times amount thrown." , null);
        Card c7 = new Card(Card.cardType.CHANCE, "Bank pays you dividend of $50", null);
        Card c8 = new Card(Card.cardType.CHANCE, "Get out of jail free", null);
        Card c9 = new Card(Card.cardType.CHANCE, "Go back 3 spaces", null);
        Card c10 = new Card(Card.cardType.CHANCE, "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200", null);
        Card c11 = new Card(Card.cardType.CHANCE, "Make general repairs on all your property. For each house pay $25. For each hotel pay $100", null);
        Card c12 = new Card(Card.cardType.CHANCE, "Speeding fine $15", null);
        Card c13 = new Card(Card.cardType.CHANCE, "Take a trip to Reading Railroad. If you pass Go, collect $200", null);
        Card c14 = new Card(Card.cardType.CHANCE, "You have been elected Chairman of the Board. Pay each player $50", null);
        Card c15 = new Card(Card.cardType.CHANCE, "Your building loan matures. Collect $150", null);

        Card c16 = new Card(Card.cardType.COMMUNITY_CHEST, "Advance to Go (Collect $200)", null);
        Card c17 = new Card(Card.cardType.COMMUNITY_CHEST, "Bank error in your favor. Collect $200", null);
        Card c18 = new Card(Card.cardType.COMMUNITY_CHEST, "Doctor’s fee. Pay $50", null);
        Card c19 = new Card(Card.cardType.COMMUNITY_CHEST, "From sale of stock you get $50", null);
        Card c20 = new Card(Card.cardType.COMMUNITY_CHEST, "Get Out of Jail Free", null);
        Card c21 = new Card(Card.cardType.COMMUNITY_CHEST, "Go to Jail. Go directly to jail, do not pass Go, do not collect $200", null);
        Card c22 = new Card(Card.cardType.COMMUNITY_CHEST, "Holiday fund matures. Receive $100", null);
        Card c23 = new Card(Card.cardType.COMMUNITY_CHEST, "Income tax refund. Collect $20", null);
        Card c24 = new Card(Card.cardType.COMMUNITY_CHEST, "It is your birthday. Collect $10 from every player", null);
        Card c25 = new Card(Card.cardType.COMMUNITY_CHEST, "Life insurance matures. Collect $100", null);
        Card c26 = new Card(Card.cardType.COMMUNITY_CHEST, "Pay hospital fees of $100", null);
        Card c27 = new Card(Card.cardType.COMMUNITY_CHEST, "Pay school fees of $50", null);
        Card c28 = new Card(Card.cardType.COMMUNITY_CHEST, "Receive $25 consultancy fee", null);
        Card c29 = new Card(Card.cardType.COMMUNITY_CHEST, "You are assessed for street repair. $40 per house. $115 per hotel", null);
        Card c30 = new Card(Card.cardType.COMMUNITY_CHEST, "You have won second prize in a beauty contest. Collect $10", null);
        Card c31 = new Card(Card.cardType.COMMUNITY_CHEST, "You inherit $100", null);
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
