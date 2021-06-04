package Controllers;

import Models.Board;
import Models.MainMenu;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import org.apache.log4j.chainsaw.Main;

public class MainMenuController implements Controller {

    private static MainMenuController mainMenuController;
    private MainMenu mainMenu;

    private MainMenuController() {
        mainMenu = new MainMenu();
    }

    public static MainMenuController getInstance() {
        if(mainMenuController == null) {
            mainMenuController = new MainMenuController();
        }
        return mainMenuController;
    }

    @Override
    public void registerObserver(View v) {
        mainMenu.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        mainMenu.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        mainMenu.notifyObservers(ds);
    }
}
