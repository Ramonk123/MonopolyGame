package Controllers;

import Models.Player;
import Models.Throw;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class ThrowController implements Controller {

    private static ThrowController throwController;
    private Throw currentThrow;

    private ThrowController() {
        currentThrow  = new Throw();
    }

    public static ThrowController getInstance() {
        if(throwController == null) {
            throwController = new ThrowController();
        }
        return throwController;
    }

    @Override
    public void registerObserver(View v) {
        currentThrow.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        currentThrow.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        currentThrow.notifyObservers(ds);
    }
}
