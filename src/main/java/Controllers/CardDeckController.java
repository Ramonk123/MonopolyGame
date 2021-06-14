package Controllers;

import Models.Actions;
import Models.Card;
import Models.CardDeck;
import Monopoly.UUID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the CardDeck model.
 */
public class CardDeckController implements Controller {

    private CardDeck cardDeck;

    private final static UUID CHANCE_JAIL_CARD_UUID = new UUID("CARD-8");
    private final static UUID COMMUNITY_JAIL_CARD_UUID = new UUID("CARD-20");

    CardDeck chanceCardDeck = new CardDeck(new ArrayList());
    CardDeck commonFundCardDeck = new CardDeck(new ArrayList<>());

    public CardDeckController() {
        setCardDecks();
    }

// TODO:
//  1. Update index on .teleportToLocation()
//  2. create TP to station and utility methods in Actions.java
//  3. implement get out of jail free card
//  4. implement go to jail card
//  5. implement make general repairs for property
//  6. Initialize
    public void setCardDecks() {
        chanceCardDeck.add(new Card(new UUID("CARD-1"), "Advance to Boardwalk", (player -> { Actions.teleportToLocation(player,0); })));
        chanceCardDeck.add(new Card(new UUID("CARD-2"), "Advance to go (collect $200)", (player -> { Actions.teleportToLocation(player,0); })));
        chanceCardDeck.add(new Card(new UUID("CARD-3"), "Advance to Illinois Avenue. If you pass go, collect $200", (player -> { Actions.teleportToLocation(player,0); })));
        chanceCardDeck.add(new Card(new UUID("CARD-4"), "Advance to St. Charles Place. If you pass go, collect $200", (player -> { Actions.teleportToLocation(player,0); })));
        chanceCardDeck.add(new Card(new UUID("CARD-5"), "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled.", Actions::teleportToNearestRailroad));
        chanceCardDeck.add(new Card(new UUID("CARD-6"), "Advance to the nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total of ten times amount thrown.", Actions::teleportToNearestUtility));
        chanceCardDeck.add(new Card(new UUID("CARD-7"), "Bank pays you dividend of $50", (player -> { Actions.receiveFunds(player, 50); })));
        chanceCardDeck.add(new Card(CardDeckController.CHANCE_JAIL_CARD_UUID, "Get out of jail free", Actions::exampleAction));
        chanceCardDeck.add(new Card(new UUID("CARD-9"), "Go back 3 spaces", Actions::goBackThreeSpaces));
        chanceCardDeck.add(new Card(new UUID("CARD-10"), "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200", Actions::exampleAction));
        chanceCardDeck.add(new Card(new UUID("CARD-11"), "Make general repairs on all your property. For each house pay $25. For each hotel pay $100", Actions::exampleAction));
        chanceCardDeck.add(new Card(new UUID("CARD-12"), "Speeding fine $15", (player -> { Actions.payFunds(player, 15); })));
        chanceCardDeck.add(new Card(new UUID("CARD-13"), "Take a trip to Reading Railroad. If you pass Go, collect $200", (player -> { Actions.teleportToLocation(player,0); })));
        chanceCardDeck.add(new Card(new UUID("CARD-14"), "You have been elected Chairman of the Board. Pay each player $50", Actions::payEachPlayer));
        chanceCardDeck.add(new Card(new UUID("CARD-15"), "Your building loan matures. Collect $150", (player -> { Actions.receiveFunds(player, 150);})));

        commonFundCardDeck.add(new Card(new UUID("CARD-16"), "Advance to Go (Collect $200)", (player -> { Actions.teleportToLocation(player, 0); })));
        commonFundCardDeck.add(new Card(new UUID("CARD-17"), "Bank error in your favor. Collect $200", (player -> { Actions.receiveFunds(player, 200);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-18"), "Doctor’s fee. Pay $50", (player -> {Actions.payFunds(player, 50);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-19"), "From sale of stock you get $50", (player -> {Actions.receiveFunds(player, 50);})));
        commonFundCardDeck.add(new Card(CardDeckController.COMMUNITY_JAIL_CARD_UUID, "Get Out of Jail Free", Actions::exampleAction));
        commonFundCardDeck.add(new Card(new UUID("CARD-21"), "Go to Jail. Go directly to jail, do not pass Go, do not collect $200", Actions::exampleAction));
        commonFundCardDeck.add(new Card(new UUID("CARD-22"), "Holiday fund matures. Receive $100", (player -> {Actions.receiveFunds(player,100);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-23"), "Income tax refund. Collect $20", (player -> {Actions.receiveFunds(player,20);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-24"), "It is your birthday. Collect $10 from every player", Actions::receiveFromEachPlayer));
        commonFundCardDeck.add(new Card(new UUID("CARD-25"), "Life insurance matures. Collect $100", (player -> {Actions.receiveFunds(player,100);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-26"), "Pay hospital fees of $100", (player -> {Actions.payFunds(player,100);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-27"), "Pay school fees of $50", (player -> {Actions.payFunds(player,50);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-28"), "Receive $25 consultancy fee", (player -> {Actions.receiveFunds(player,25);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-29"), "You are assessed for street repair. $40 per house. $115 per hotel", Actions::exampleAction));
        commonFundCardDeck.add(new Card(new UUID("CARD-30"), "You have won second prize in a beauty contest. Collect $10", (player -> {Actions.receiveFunds(player,10);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-31"), "You inherit $100", (player -> {Actions.receiveFunds(player,100);})));

        chanceCardDeck.shuffle();
        commonFundCardDeck.shuffle();
    }

    // New method, player grabs card directly, host updates from his deck.

    // In this function the player grabs the card.
    public Card grabChanceCard() throws ExecutionException, InterruptedException {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        return fireStoreController.getChanceCard();
    }

    public Card grabCommonFundCard() throws ExecutionException, InterruptedException {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        return fireStoreController.getCommonFundCard();
    }

    public void returnCard(Card card) {
        if(UUID.compare("CARD-8", card.getId())) {
            chanceCardDeck.add(card);
        }
        if(UUID.compare("CARD-20", card.getId())) {
            commonFundCardDeck.add(card);
        }
    }

    //Only meant for host.
    public Card getNextChanceCard() {
        return chanceCardDeck.getCardDeck().get(0);
    }

    public Card getNextCommonFundCard() {
        return commonFundCardDeck.getCardDeck().get(0);
    }

    // Not in use moght be deleted later.
    public ArrayList<UUID> returnUUID(CardDeck arrayListCard) {
        ArrayList<UUID> arrayListUUID = new ArrayList<>();
        for(int i = 0; arrayListCard.getCardDeck().size() >= i; i++){
            arrayListUUID.add(arrayListCard.getCardDeck().get(i).getId());
        }
        return arrayListUUID;
    }
}
