package Controllers;

import Models.Board;
import Models.MainMenu;
import Views.MainMenuView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.chainsaw.Main;

import java.io.IOException;

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

    //Join Lobby
    @FXML
    private void goToJoinLobby(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/JoinLobbyView.fxml"));
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //Create Lobby
    @FXML
    private void goToCreateLobby(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/CreateLobbyView.fxml"));
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Quit button
    @FXML
    private Pane ConfirmQuitScreen;
    @FXML
    public void displayQuitScreen() {
        ConfirmQuitScreen.setVisible(!ConfirmQuitScreen.isVisible());
    }
    @FXML
    public void ConfirmQuitGame() {
        mainMenu.quitGame();
    }
}
