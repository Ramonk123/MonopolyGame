package Controllers;

import Models.*;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Resetter.Resettable;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the CardDeck model which creates all cards and puts them in
 */
public class CardDeckController
        implements
        Observer<DocumentSnapshot>,
            Subject<DocumentSnapshot>,
            Resettable, Controller {

    private CardDeck cardDeck;

    private final static UUID CHANCE_JAIL_CARD_UUID = new UUID("CARD-8");
    private final static UUID COMMUNITY_JAIL_CARD_UUID = new UUID("CARD-20");

    private CardDeck chanceCardDeck = new CardDeck(new ArrayList());
    private CardDeck commonFundCardDeck = new CardDeck(new ArrayList<>());
    private DocumentSnapshot documentSnapshot;

    public CardDeckController() {
        setCardDecks();
    }

    public void setCardDecks() {
        chanceCardDeck.add(new Card(new UUID("CARD-1"), "Advance to Steenstraat", (player -> { Actions.teleportToLocation(player,6, false); })));
        chanceCardDeck.add(new Card(new UUID("CARD-2"), "Advance to go (collect $200)", (player -> { Actions.teleportToLocation(player,0, true); })));
        chanceCardDeck.add(new Card(new UUID("CARD-3"), "Advance to Grote markt. If you pass go, collect $200", (player -> { Actions.teleportToLocation(player,23, true); })));
        chanceCardDeck.add(new Card(new UUID("CARD-4"), "Advance to Zijlweg. If you pass go, collect $200", (player -> { Actions.teleportToLocation(player,13, true); })));
        chanceCardDeck.add(new Card(new UUID("CARD-5"), "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled.", Actions::teleportToNearestRailroad));
        chanceCardDeck.add(new Card(new UUID("CARD-6"), "Advance to the nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total of ten times amount thrown.", Actions::teleportToNearestUtility));
        chanceCardDeck.add(new Card(new UUID("CARD-7"), "Bank pays you dividend of $50", (player -> { Actions.receiveFunds(player, 50); })));
        chanceCardDeck.add(new Card(new UUID("CARD-9"), "Go back 3 spaces", Actions::goBackThreeSpaces));
        chanceCardDeck.add(new Card(new UUID("CARD-10"), "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200", Actions::goToJail));
        chanceCardDeck.add(new Card(new UUID("CARD-11"), "Make general repairs on all your property. For each house pay $25. For each hotel pay $100", (player -> {Actions.makeRepairs(player,100,25);})));
        chanceCardDeck.add(new Card(new UUID("CARD-12"), "Speeding fine $15", (player -> { Actions.payFunds(player, 15); })));
        chanceCardDeck.add(new Card(new UUID("CARD-13"), "Take a trip to Station Zuid. If you pass Go, collect $200", (player -> { Actions.teleportToLocation(player,5, true); })));
        chanceCardDeck.add(new Card(new UUID("CARD-14"), "You have been elected Chairman of the Board. Pay each player $50", Actions::payEachPlayer));
        chanceCardDeck.add(new Card(new UUID("CARD-15"), "Your building loan matures. Collect $150", (player -> { Actions.receiveFunds(player, 150);})));

        commonFundCardDeck.add(new Card(new UUID("CARD-16"), "Advance to Go (Collect $200)", (player -> { Actions.teleportToLocation(player, 0, true); })));
        commonFundCardDeck.add(new Card(new UUID("CARD-17"), "Bank error in your favor. Collect $200", (player -> { Actions.receiveFunds(player, 200);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-18"), "Doctor’s fee. Pay $50", (player -> {Actions.payFunds(player, 50);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-19"), "From sale of stock you get $50", (player -> {Actions.receiveFunds(player, 50);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-21"), "Go to Jail. Go directly to jail, do not pass Go, do not collect $200", Actions::goToJail));
        commonFundCardDeck.add(new Card(new UUID("CARD-22"), "Holiday fund matures. Receive $100", (player -> {Actions.receiveFunds(player,100);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-23"), "Income tax refund. Collect $20", (player -> {Actions.receiveFunds(player,20);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-24"), "It is your birthday. Collect $10 from every player", Actions::receiveFromEachPlayer));
        commonFundCardDeck.add(new Card(new UUID("CARD-25"), "Life insurance matures. Collect $100", (player -> {Actions.receiveFunds(player,100);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-26"), "Pay hospital fees of $100", (player -> {Actions.payFunds(player,100);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-27"), "Pay school fees of $50", (player -> {Actions.payFunds(player,50);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-28"), "Receive $25 consultancy fee", (player -> {Actions.receiveFunds(player,25);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-29"), "You are assessed for street repair. $40 per house. $115 per hotel", (player -> { Actions.makeRepairs(player,115,40);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-30"), "You have won second prize in a beauty contest. Collect $10", (player -> {Actions.receiveFunds(player,10);})));
        commonFundCardDeck.add(new Card(new UUID("CARD-31"), "You inherit $100", (player -> {Actions.receiveFunds(player,100);})));

        chanceCardDeck.shuffle();
        commonFundCardDeck.shuffle();
    }

    // New method, player grabs card directly, host updates from his deck.

    // In this function the player grabs the card.
    public Card grabChanceCard() throws ExecutionException, InterruptedException {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
        String CardUUIDString = fireStoreController.getChanceCard(lobbyController.getToken());
        for(int i = 0; chanceCardDeck.size() > i; i++){
            if(UUID.compare(CardUUIDString, chanceCardDeck.getCardDeck().get(i).getId())){
                return chanceCardDeck.getCardDeck().get(i);
            }
        }
        return null;
    }

    public Card grabCommonFundCard() throws ExecutionException, InterruptedException {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        String CardUUIDString = fireStoreController.getCommonFundCard();
        for(int i = 0; commonFundCardDeck.size() > i; i++){
            if(UUID.compare(CardUUIDString, commonFundCardDeck.getCardDeck().get(i).getId())){
                return commonFundCardDeck.getCardDeck().get(i);
            }
        }
        return null;
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
    public String getNextChanceCard() {
        String nextCard = chanceCardDeck.getCardDeck().get(0).getId().getId();
        chanceCardDeck.take(0, chanceCardDeck.getCardDeck().get(0));
        return nextCard;
    }

    public String getNextCommonFundCard() {
        String nextCard = commonFundCardDeck.getCardDeck().get(0).getId().getId();
        commonFundCardDeck.take(0, commonFundCardDeck.getCardDeck().get(0));
        return nextCard;
    }


    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void notifyObservers() { }

    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        String ChanceCard = (String) documentSnapshot.get("nextChanceCard");
        String CommonFundCard = (String) documentSnapshot.get("nextCommonFundCard");
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        if (playerController.getClientPlayersEnum() == Players.PLAYER_ONE) {
            if(ChanceCard == null){
                fireStoreController.updateChanceCard();
            }
            if(CommonFundCard == null){
                fireStoreController.updateCommonFundCard();
            }
        }
    }

    @Override
    public void reset() {

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
