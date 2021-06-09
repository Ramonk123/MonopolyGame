package Models;

import Controllers.ControllerRegistry;
import Controllers.MainMenuController;
import Controllers.PlayerController;

public class Actions {

    public static void exampleAction(Player p) {
        System.out.println("An example action has been performed.");
    }

    public static void teleportToBoardWalk(Player player) {
        final int BOARDWALK_INDEX = 40; //TODO: Set right index for location
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, BOARDWALK_INDEX);
    }

    public static void teleportToGo(Player player) {
        final int GO_INDEX = 0;
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, GO_INDEX);
    }

    public static void teleportToIllinois(Player player) {
        final int ILLINOIS_INDEX = 0; //TODO: Set right index for location
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, ILLINOIS_INDEX);
    }

    public static void teleportToStCharles(Player player) {
        final int ST_CHARLES_INDEX = 0; //TODO: Set right index for location
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, ST_CHARLES_INDEX);
    }

    public static void teleportToNearestRailroad(Player player) {

    }

}
