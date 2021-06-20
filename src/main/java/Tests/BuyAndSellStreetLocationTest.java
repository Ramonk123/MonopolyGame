package Tests;

import Controllers.*;
import Exceptions.TransactionException;
import Models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.List;

public class BuyAndSellStreetLocationTest {

    @Test //Only works when 2 FXML related lines in BoardController are commented out
    public void Should_Return_True_On_Is_Owned_When_Street_Location_Is_Bought(){
        //Arrange
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new PlayerController());
        ControllerRegistry.register(new BoardController());

        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Rick");

        Player player = playerController.getPlayers().get(0);
        List<OwnableLocation> locations = locationController.getOwnableLocations();


        //ACT
        boardController.buyLocation(player, locations.get(0));

        //Assert
        Assertions.assertTrue(locations.get(0).isOwned());
    }

    @Test
    public void Should_Return_False_On_Is_Owned_When_Street_Location_Is_Sold(){
        //Arrange
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new PlayerController());
        ControllerRegistry.register(new BoardController());

        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Rick");

        Player player = playerController.getPlayers().get(0);
        List<StreetLocation> streetLocations = locationController.getStreetLocationArray();


        //ACT
        boardController.sellStreetLocation(player, streetLocations.get(0));

        //Assert
        Assertions.assertFalse(streetLocations.get(0).isOwned());
    }
}
