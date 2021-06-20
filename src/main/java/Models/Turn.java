package Models;

import Controllers.*;
import Exceptions.PlayerException;
import Firestore.FirestoreFormattable;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

// still needs work

/**
 * Model that contains all rules and information of a turn.
 * The player that is "active" is the Player that can roll dice and do other actions that inactive players can't do.
 */
public class Turn implements FirestoreFormattable, Observer<DocumentSnapshot> {
    private Players activePlayer = Players.PLAYER_ONE;
    private int amountOfDouble = 0;
    private long eyesThrown = 0;

    public Turn() {

    }

    public void startGameTurn() {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);

        if(UUID.compare(playerController.getClientPlayersEnum(), activePlayer)) {
            boardController.toggleRollDiceButton(true);
            boardController.toggleEndTurnButton(true);
        }
    }

    /**
     * Sets the currentPlayer and makes the buttons visible for the currentPlayer.
     * @param players The new currentPlayer.
     */
    public void setCurrentPlayer(Players players) {
        // players == client player && players != activePlayer
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);

        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        System.out.println("Player is already the active player: " + !UUID.compare(players, turnController.getCurrentPlayer()));
        System.out.println("Player is the client player: " + UUID.compare(players, playerController.getClientPlayersEnum()));

        if(UUID.compare(players, activePlayer)) {
            return;
        }

        this.activePlayer = players;

        if(UUID.compare(playerController.getClientPlayersEnum(), activePlayer)) {
            try {
                boardController.toggleRollDiceButton(true);
                boardController.toggleEndTurnButton(true);
                System.out.println("----Buttons turned on----");
            } catch(Exception ignored) {}
        }
    }

    public Players getCurrentPlayer()
    {
        return activePlayer;
    }

    @Override
    public Object getFirestoreFormat() {
        Map<String, Object> map = new HashMap<>();
        map.put("activePlayer", activePlayer.getId().getId());
        map.put("amountOfDoubles", amountOfDouble);
        map.put("eyesThrown", eyesThrown);
        return map;
    }

    public void addOneToAmountOfDouble() {
        amountOfDouble += 1;
    }

    public void resetAmountOfDouble() {
        amountOfDouble = 0;
    }

    public int getAmountOfDouble() {
        return amountOfDouble;
    }

    @Override
    public void update(DocumentSnapshot state) {
        try {
            Map<String, Object> map = (Map<String, Object>) state.get("turn");
            assert map != null;
            setCurrentPlayer(Players.getByStringUuid((String) map.get("activePlayer"))
                    .orElseThrow(() -> new PlayerException("Player Id doesn't exist.")));
            //amountOfDouble = (int) (long) map.get("amountOfDoubles");
            eyesThrown = (long) map.get("eyesThrown");

        } catch(PlayerException playerException) {
            playerException.printStackTrace();
        }

    }

    public void setEyesThrown(long eyesThrown) {
        this.eyesThrown = eyesThrown;
    }

    public void addEyesThrown(long eyesThrown) {
        this.eyesThrown += eyesThrown;
    }

    public long getEyesThrown() {
        return eyesThrown;
    }
}
