package Controllers;

import Exceptions.PlayerException;
import Firestore.FirestoreFormattable;
import Models.Player;
import Models.Turn;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Resetter.Resettable;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Turn model, mainly controls the flow of actions that the player can do.
 */
public class TurnController
        implements
            Controller,
            FirestoreFormattable,
            Observer<DocumentSnapshot>,
            Resettable,
            Subject<DocumentSnapshot> {

    private Turn turn = new Turn();
    private DocumentSnapshot documentSnapshot;

    public TurnController() {

    }

    public Players getCurrentPlayer() {
        return turn.getCurrentPlayer();
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
        System.out.println(map.containsKey("eyesThrown"));
        turn.setEyesThrown((long) map.get("eyesThrown"));
        System.out.println("testerst");
        //notifyObservers();
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
    }

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
                boardController.setRollDiceVisibility(false);
            } else {
                boardController.setRollDiceVisibility(true);
            } // Don't simplify this yet.
            movePlayer(currentPlayerEnum, turn.getEyesThrown());

            boardController.setDiceLabelPane();

            LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
            FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
            try {
                fireStoreController.updatePlayer(lobbyController.getToken(), currentPlayer);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void movePlayer(Players currentPlayerEnum, long eyesThrown) throws PlayerException {
        Player currentPlayer = ((PlayerController) ControllerRegistry.get(PlayerController.class)).getPlayerByPlayersEnum(currentPlayerEnum).orElseThrow(() -> new PlayerException("Player NOT Found"));

        currentPlayer.movePlayer(eyesThrown);
        long oldPlayerPosition = currentPlayer.getOldPosition();
        long newPlayerPosition = currentPlayer.getPosition();

        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        boardController.movePlayerOnBoard(currentPlayerEnum, oldPlayerPosition, newPlayerPosition);

    }
}
