package Models;

import Controllers.*;
import Exceptions.TransactionException;
import Monopoly.UUID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model for all actions of cards and locations.
 */
public class Actions {

    public static void exampleAction(Player player) {
        System.out.println("An example action has been performed.");
    }


    public static void teleportToLocation(Player player, long position) {
        PlayerController playerController = (PlayerController)ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, position);
    }

    public static void teleportToNearestRailroad(Player player) {
        PlayerController playerController = (PlayerController)ControllerRegistry.get(PlayerController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        long playerPosition = player.getPosition();
        List<OwnableLocation> railRoadLocations = locationController.getRailroadLocations();
        ArrayList<Long> stepsToRailroad = new ArrayList<>();

        for(OwnableLocation location : railRoadLocations) {
            long railRoadPosition = location.getPosition();
            stepsToRailroad.add((railRoadPosition - playerPosition));
        }
        long nearestRailroad = (stepsToRailroad.indexOf(Collections.min(stepsToRailroad)) + playerPosition);
        playerController.teleportTo(player, nearestRailroad);




    }

    public static void teleportToNearestUtility(Player player) {
        PlayerController playerController = (PlayerController)ControllerRegistry.get(PlayerController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        long playerPosition = player.getPosition();
        List<OwnableLocation> utilityLocations = locationController.getUtilityLocations();
        ArrayList<Long> stepsToUtility = new ArrayList<>();

        for(OwnableLocation location : utilityLocations) {
            long utilityPosition = location.getPosition();
            stepsToUtility.add((utilityPosition - playerPosition));
        }
        long nearestUtility = (stepsToUtility.indexOf(Collections.min(stepsToUtility)) + playerPosition);
        playerController.teleportTo(player, nearestUtility);
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
        player.movePlayer(-3);
    }

    public static void payEachPlayer(Player player) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        int amount = 50;
        for(Player eachPlayer: playerController.getPlayers()) {
            try {
                if(!UUID.compare(player,eachPlayer)) {
                    if(player.checkBalance(amount)){
                        transactionController.payBalance(player.getPlayersEnum(), eachPlayer.getPlayersEnum(), amount);
                    } else {break;}

                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void receiveFromEachPlayer(Player player) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        int amount= 10;
        for(Player eachPlayer: playerController.getPlayers()) {
            try {
                if(!UUID.compare(player,eachPlayer))
                    if(eachPlayer.checkBalance(amount)) {
                        transactionController.payBalance(eachPlayer.getPlayersEnum(),player.getPlayersEnum(),amount);
                    } else {break;}

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void goToJail(Player player) {
        player.setPosition(30);
    }

    public static void makeRepairs(Player player, int hotelCost, int houseCost) {
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        List<StreetLocation> locations = locationController.getStreetLocationsOwnedByPlayer(player.getId());
        int total = 0;

        if(locations.size() != 0) {
            for (StreetLocation location : locations) {
                if(location.getHotel()) {
                    total += hotelCost;
                }
                else if(location.getHouses() > 0) {
                    total += (location.getHouses() * houseCost );
                }
            }
            try {
                transactionController.subtractBalance(player.getPlayersEnum(), total);
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
    }


}
