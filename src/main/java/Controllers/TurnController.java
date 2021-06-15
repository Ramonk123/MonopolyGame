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

public class TurnController
        implements
            Controller,
            FirestoreFormattable,
            Observer<DocumentSnapshot>,
            Resettable,
            Subject<DocumentSnapshot> {

    private Turn turn;
    private DocumentSnapshot documentSnapshot;
    private long eyesThrown;

    public TurnController() {
        turn = new Turn();
        eyesThrown = 0;
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
        System.out.println(map.containsKey("eyesThrown"));
        eyesThrown = (long) map.get("eyesThrown");
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
    public void unregisterObserver(Observer<DocumentSnapshot> observer) {
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
            int amountThrown = throwController.getTotalEyes();
            int thrownDouble = 0;
            if(throwController.isDouble()) {
                thrownDouble++;
                while(throwController.isDouble()) {
                    if(thrownDouble >= 3) {
                        //TODO: Go to Jail
                        break;
                    }
                    throwController.throwDice();
                    amountThrown += throwController.getTotalEyes();
                    thrownDouble++;
                }
            }
            movePlayer(currentPlayerEnum, amountThrown);
        }

    }

    public void movePlayer(Players currentPlayerEnum, int amountThrown) throws PlayerException {
        Player currentPlayer = ((PlayerController) ControllerRegistry.get(PlayerController.class)).getPlayerByPlayersEnum(currentPlayerEnum).orElseThrow(() -> new PlayerException("Player NOT Found"));

        int oldPlayerPosition = currentPlayer.getPosition();
        currentPlayer.movePlayer(amountThrown);
        int newPlayerPosition = currentPlayer.getPosition();

        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        boardController.movePlayerOnBoard(currentPlayerEnum, oldPlayerPosition, newPlayerPosition);

    }
}
