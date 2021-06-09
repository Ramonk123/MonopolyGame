package Controllers;

import Models.Player;
import Models.Turn;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class TurnController implements Controller {

    private Turn turn;

    private TurnController() {
        turn = new Turn();
    }

}
