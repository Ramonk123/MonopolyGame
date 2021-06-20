package Controllers;

import Exceptions.PlayerException;
import Firestore.FirestoreFormattable;
import Models.*;
import Models.Location;
import Models.Player;
import Models.Turn;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Resetter.Resettable;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Turn model, mainly controls the flow of actions that the player can do.
 */
public class TurnController
        implements
        FirestoreFormattable,
            Observer<DocumentSnapshot>,
            Resettable,
            Subject<DocumentSnapshot>, Controller {

    private final Turn turn = new Turn();
    private final Player player = new Player(getCurrentPlayer(), getCurrentPlayer().name());
    private DocumentSnapshot documentSnapshot;

    public static boolean chanceCardUsed = false;
    public static boolean communityCardUsed = false;

    public TurnController() {

    }

    public void startGameTurn() {
        turn.startGameTurn();
    }

    public Players getCurrentPlayer() {
        return turn.getCurrentPlayer();
    }
    public void setCurrentPlayer(Players playersEnum) {
        turn.setCurrentPlayer(playersEnum);
    }

    /**
     * Sets a new currentPlayer based on the playerArray.
     */
    public void nextPlayerTurn() {
        //turn.resetAmountOfDouble();
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        Players playersEnum = playerController.getClientPlayersEnum();
        Player player = null;
        try {
            player = playerController.getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new Exception("no player found"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //playerController.sortPlayerArrayList();
        List<Player> list = playerController.getPlayers();
        int index = list.indexOf(player);
        int size = list.size();
        System.out.println("Index: " + index);
        System.out.println("Size: " + size);
        Player nextPlayer;
        for (Player value : list) {
            System.out.println("Index: " + index + "ID: " + value.getId().getId());
        }
        try {
            nextPlayer = list.get(index + 1);
        } catch(Exception e) {
            nextPlayer = playerController.getPlayerFromPlayersByEnum(list,Players.PLAYER_ONE);
        }

        setCurrentPlayer(nextPlayer.getPlayersEnum());

        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        Players players = turnController.getCurrentPlayer();
        Player player1 = playerController.getPlayerByPlayersEnum(players).orElseThrow();
        if(hasLostTheGame(player1)) {
            nextPlayerTurn();
        }
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);

        fireStoreController.updateTurn(lobbyController.getToken(), turn);
    }


    public void checkJailedStatus() {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        Player player = playerController.getPlayerByPlayersEnum(getCurrentPlayer()).orElseThrow();
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        if(player.isInJail()) {
            player.setTurnInJail();
            Platform.runLater(()->{
                boardController.getInJailPopup().setVisible(true);
                boardController.toggleEndTurnButton(true);
                boardController.toggleRollDiceButton(false);
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                boardController.getInJailPopup().setVisible(false);
                            }
                        },
                        2000
                );
            });



        }
    }

    @Override
    public Object getFirestoreFormat() {
        //Map<String, Object> map = new HashMap<>();
        return turn.getFirestoreFormat();
    }

    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        Map<String, Object> map = (Map<String, Object>) state.get("turn");
        System.out.println(map);
        assert map != null;
        System.out.println("EyesThrown by current player " + map.get("eyesThrown"));
        notifyObservers();
    }

    @Override
    public void reset() {

    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) {
    }

    @Override
    public void notifyObservers() {
        turn.update(documentSnapshot);
        //checkJailedStatus();
    }

    /**
     * This method controlls the flow of rolling a dice and partly what happens after.
     * @throws PlayerException
     */
    public void RollDice() throws PlayerException {
        Players currentPlayerEnum = getCurrentPlayer();

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        Players clientPlayerEnum = playerController.getClientPlayersEnum();

        Player currentPlayer = null;
        try {
            currentPlayer = playerController.getPlayerByPlayersEnum(currentPlayerEnum).orElseThrow(() -> new Exception("Player doesn't exist."));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(UUID.compare(currentPlayerEnum, clientPlayerEnum)) {
            BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
            ThrowController throwController = (ThrowController) ControllerRegistry.get(ThrowController.class);
            throwController.throwDice();
            turn.setEyesThrown(throwController.getTotalEyes());

            if(!throwController.isDouble()) {
                boardController.toggleRollDiceButton(false);
                boardController.toggleEndTurnButton(true);

            } else {
                turn.addOneToAmountOfDouble();
            } // Don't simplify this yet.

            if(turn.getAmountOfDouble() >=3) {
                Actions.goToJail(playerController.getPlayerByPlayersEnum(clientPlayerEnum).orElseThrow());
            } else {
                movePlayer(currentPlayerEnum, turn.getEyesThrown());
            }

            boardController.setDiceLabelPane();

            long oldPosition = currentPlayer.getOldPosition();


            standingOnLocation(currentPlayer);

            currentPlayer.setOldPosition(oldPosition);


            LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
            FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);

            LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
            locationController.testPrintLocationsOwned();
            try {
                assert currentPlayer != null;
                fireStoreController.updateAllPlayers(lobbyController.getToken(), playerController.getPlayers());
                fireStoreController.updateTurn(lobbyController.getToken(), turn);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }standingOnLocation(player);
        }
    }

    /**
     * Detects on which location you are standing and activates its action.
     * @param player Player is the old currentPlayer because Firestore
     */
    public void standingOnLocation(Player player) {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        Player currentPlayer = ((PlayerController) ControllerRegistry.get(PlayerController.class)).getPlayerByPlayersEnum(getCurrentPlayer()).orElseThrow();
        Location location = boardController.playerStandsOn(currentPlayer);
        System.out.println(location.getName() + "location hier");
        location.action(player);
    }

    /**
     * Moves the player on the back-end.
     * @param currentPlayerEnum The enum of the currentPlayer.
     * @param eyesThrown The amount of eyes thrown.
     * @throws PlayerException
     */
    public void movePlayer(Players currentPlayerEnum, long eyesThrown) throws PlayerException {
        Player currentPlayer = ((PlayerController) ControllerRegistry.get(PlayerController.class)).getPlayerByPlayersEnum(currentPlayerEnum).orElseThrow(() -> new PlayerException("Player NOT Found"));

        currentPlayer.movePlayer(eyesThrown);
        /*
        long oldPlayerPosition = currentPlayer.getOldPosition();
        long newPlayerPosition = currentPlayer.getPosition();

        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        boardController.movePlayerOnBoard(currentPlayerEnum, oldPlayerPosition, newPlayerPosition);
         */
    }

    /**
     * Moves the player on the front-end.
     * @param currentPlayerEnum The enum of the currentPlayer.
     * @throws PlayerException
     */
    public void movePlayerOnBoard(Players currentPlayerEnum) throws PlayerException {
        Player currentPlayer = ((PlayerController) ControllerRegistry.get(PlayerController.class)).getPlayerByPlayersEnum(currentPlayerEnum).orElseThrow(() -> new PlayerException("Player NOT Found"));

        long oldPlayerPosition = currentPlayer.getOldPosition();
        long newPlayerPosition = currentPlayer.getPosition();

        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        boardController.movePlayerOnBoard(currentPlayerEnum, oldPlayerPosition, newPlayerPosition);
    }

    public long getEyesThrown() {
        return turn.getEyesThrown();
    }

    /**
     * Detects if the player give has lost the game or if there is still a chance to not lose.
     * @param player The player in question that could be losing.
     * @return Returns true if the player has lost, false if the player still has a chance.
     */
    public boolean hasLostTheGame(Player player) {
        return player.getWallet().getBalance() <= 0;
    }

}
