package Models;

import Controllers.*;
import Monopoly.UUID;

/**
 * Model for all actions of cards and locations.
 */
public class Actions {

    public static void exampleAction(Player player) {
        System.out.println("An example action has been performed.");
    }

    public static void teleportToLocation(Player player, int index) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, index);
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
            transactionController.addBalance(player.getPlayersEnum(), amount);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void payFunds(Player player, int amount) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        try{
            transactionController.subtractBalance(player.getPlayersEnum(), amount);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void goBackThreeSpaces(Player player) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        try{playerController.movePlayer(player.getName(),-3);}
        catch(Exception e) {System.out.println(e);
        }
    }

    public static void payEachPlayer(Player player) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        for(Player eachPlayer: playerController.getPlayers()) {
            try {
                if(!UUID.compare(player,eachPlayer))
                transactionController.payBalance(player.getPlayersEnum(), eachPlayer.getPlayersEnum(), 50);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void receiveFromEachPlayer(Player player) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        for(Player eachPlayer: playerController.getPlayers()) {
            try {
                if(!UUID.compare(player,eachPlayer))
                    transactionController.payBalance(eachPlayer.getPlayersEnum(),player.getPlayersEnum(),10);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }













































































































    public static void startAuction(OwnableLocation ownableLocation, Player player){
        AuctionController auctionController = (AuctionController) ControllerRegistry.get(AuctionController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        if (player.getPosition() == ownableLocation.getPosition() && ownableLocation.isOwned()==false){
            auctionController.startAuction();
        }
    }

    public static void buyLocationPopup(Player player){
            //TODO show popup
    }

    public static void goToJail(Player player){
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        try{playerController.teleportTo(player, 10);}
        catch(Exception e) {System.out.println(e);
        }
    }

}
