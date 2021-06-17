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

        if(UUID.compare(currentPlayerEnum, clientPlayerEnum)) {
            ThrowController throwController = (ThrowController) ControllerRegistry.get(ThrowController.class);
            throwController.throwDice();
            turn.setEyesThrown(throwController.getTotalEyes());
            int thrownDouble = 0;
            if(throwController.isDouble()) {
                thrownDouble++;
                while(throwController.isDouble()) {
                    if(thrownDouble >= 3) {
                        //TODO: Go to Jail
                        break;
                    }
                    throwController.throwDice();
                    turn.addEyesThrown(throwController.getTotalEyes());
                    thrownDouble++;
                }
            }
            movePlayer(currentPlayerEnum, turn.getEyesThrown());
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
