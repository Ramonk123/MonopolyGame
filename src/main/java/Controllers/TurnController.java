package Controllers;

import Models.Player;
import Models.Throw;
import Models.Turn;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class TurnController implements Controller {

    private Turn turn;

    private TurnController() {
        turn = new Turn();
    }

    public Throw getCurrentThrow() {
        ThrowController throwController = (ThrowController) ControllerRegistry.get(ThrowController.class);
        return throwController.getCurrentThrow();
    }
}
