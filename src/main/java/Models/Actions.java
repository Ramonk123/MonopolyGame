package Models;

import Controllers.ControllerRegistry;
import Controllers.MainMenuController;
import Controllers.PlayerController;
import Controllers.TransactionController;

public class Actions {

    public static void exampleAction(Player p) {
        System.out.println("An example action has been performed.");
    }

    public static void teleportToLocation(Player player, int index) {
         int BOARDWALK_INDEX = 40; //TODO: Set right index for location


        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, BOARDWALK_INDEX);
    }

    public static void teleportToNearestRailroad(Player player) {
        //TODO:
        // add functionality to method.
        // Unable to do so now because location array not implemented
    }

    public static void teleportToNearestUtility(Player player) {
        //TODO:
        // add functionality to method.
        // Unable to do so now because location array not implemented
    }

    public static void receiveFunds(Player player, int amount) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        try{
            transactionController.addBalance(player.getId(), 50);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void goBackThreeSpaces(Player player) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

    }
}
