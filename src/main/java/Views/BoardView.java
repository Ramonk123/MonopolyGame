package Views;

import Controllers.BoardController;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardView implements View, Observer<BoardSubject> {
    //Screensize
    int WIDTH = 1920;
    int HEIGHT = 1080;

    private final Stage primaryStage;

    private BoardController boardController;

    public BoardView(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            createPrimaryStage();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/BoardView.fxml"));
        loader.setController(boardController);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    @Override
    public void update(BoardSubject state) {

    }
}
