package Models;

import Controllers.*;
import Exceptions.TransactionException;
import Monopoly.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model for all actions of cards and locations.
 */
public class Actions {

    public static void exampleAction(Player player) {
        System.out.println("An example action has been performed." + player.getPosition());
    }


    public static void teleportToLocation(Player player, long position, boolean entitledToSalary) {
        PlayerController playerController = (PlayerController)ControllerRegistry.get(PlayerController.class);
        playerController.teleportTo(player, position);

    }

    public static void teleportToNearestRailroad(Player player) {
        PlayerController playerController = (PlayerController)ControllerRegistry.get(PlayerController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        long playerPosition = player.getPosition();
        List<OwnableLocation> railRoadLocations = locationController.getRailroadLocations();
        ArrayList<Long> stepsToRailroad = new ArrayList<>();

        for (OwnableLocation location : railRoadLocations) {
            stepsToRailroad.add((location.getPosition() - playerPosition));
        }
        List<Long> sortedList = stepsToRailroad.stream().sorted().collect(Collectors.toList());
        for (Long i : sortedList) {
            if(i > 0) {
                playerController.movePlayerWithPlayerObject(player, i);
                break;
            }
        }


    }

    public static void teleportToNearestUtility(Player player) {
        PlayerController playerController = (PlayerController)ControllerRegistry.get(PlayerController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        long playerPosition = player.getPosition();
        List<OwnableLocation> utilityLocations = locationController.getUtilityLocations();
        ArrayList<Long> stepsToUtility = new ArrayList<>();
        for(OwnableLocation location : utilityLocations) {
            stepsToUtility.add((location.getPosition() - playerPosition));
        }
        List<Long> sortedList = stepsToUtility.stream().sorted().collect(Collectors.toList());
        for(Long i : sortedList) {
            if (i > 0) {
                playerController.movePlayerWithPlayerObject(player, i);
                break;
            }
        }
    }

    public static void receiveFunds(Player player, int amount) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        try{
            transactionController.addBalance(player.getPlayersEnum(), amount);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void payFunds(Player player, int amount) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        try{
            transactionController.subtractBalance(player.getPlayersEnum(), amount);
        } catch(Exception e) {
            e.printStackTrace();
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



//    TODO change method to startAuctionButton, add startActionButton in BoardController
    public static void startAuction(OwnableLocation ownableLocation, Player player){
        AuctionController auctionController = (AuctionController) ControllerRegistry.get(AuctionController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        if (player.getPosition() == ownableLocation.getPosition() && !ownableLocation.isOwned()){
            auctionController.startAuction(ownableLocation.getId().getId());
        }
    }

    public static void buyLocationPopup(Player player, int price, OwnableLocation location){
        TransactionController transactionController = (TransactionController) ControllerRegistry.get((TransactionController.class));
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        try {
            if (transactionController.checkBalance(player.getPlayersEnum(), price) && !location.isOwned()){
                boardController.showBuyLocationPopup(player, location);
            } else {boardController.showNotEnoughBalance();}
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    public static void payRentPopup(Player player, StreetLocation location, PriceInflator priceInflator) {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        boardController.showStreetPayRent(player, location, priceInflator);
    }

    public static void sellLocationPopup(Player player, StreetLocation location) {
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        boardController.showSellStreetLocationPopup(player, location);
    }

    public static void goToJail(Player player){
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        try{playerController.teleportTo(player, 10);
            player.setInJail(true);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Location actions

    public static void chanceCard(Player player) {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        boardController.setChancePopupVisible(player);
    }


    public static void standingOnGo(Player player) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        try {
            transactionController.addBalance(player.getPlayersEnum(), 200);
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

}
