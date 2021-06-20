package Models;

import Controllers.ControllerRegistry;
import Controllers.PlayerController;
import Controllers.Players;
import Controllers.TurnController;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Model that contains everything that a special location needs.
 */
public class SpecialLocation extends Location {
    private final List<View> observers = new ArrayList<>();
    private Consumer<Player> action;

    public SpecialLocation(Locations locationEnum, String name, Set set, int position, Consumer<Player> action) {
        super(locationEnum, name, set, position);
        this.action = action;
    }

    @Override
    public void action(Player player) {
        this.action.accept(player);
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        Player player1 = null;
        try {
            player1 = playerController.getPlayerByPlayersEnum(turnController.getCurrentPlayer()).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long position = player1.getPosition();
        System.out.println("positie " + player1.getPosition());
        if(position == 22 || position == 7 || position == 38) {
            System.out.println("ik ben bereikt");
            Actions.chanceCard(player1);
        } else if(position == 2 || position == 17 || position == 35) {
            //TODO: Make this common funds card instead of chance card.
            Actions.chanceCard(player1);
        } else if(position == 4 || position == 33) {
            Actions.payFunds(player1, 200);
        } else if(position == 10) {
            // On Jail grid
        } else if(position == 20) {
            // Free parking
        } else if(position == 30) {
            Actions.goToJail(player1);
        } else if(position == 0) {
            Actions.standingOnGo(player1);
        }
    }

    public Consumer<Player> getAction(){return action; }
}
