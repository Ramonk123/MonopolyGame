package Tests;

import Controllers.*;
import Exceptions.TransactionException;
import Models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.List;

public class GetAndPayMortgageTest {

    @Test
    public void Should_Return_True_On_Location_Has_Mortgage_When_Taking_A_Mortgage() {
        //Arrange
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new PlayerController());

        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Ramon");

        Player player = playerController.getPlayers().get(0);
        Players playerEnum = player.getPlayersEnum();

        List<OwnableLocation> ownableLocations = locationController.getOwnableLocations();
        ownableLocations.get(1).setOwner(player, true);
        //ACT
        locationController.getMortgageOnLocation(ownableLocations.get(1));

        //Assert
        Assertions.assertTrue(ownableLocations.get(1).hasMortgage());

    }

    @Test
    public void Should_Return_False_On_Has_Mortgage_When_Paying_Mortgage() {
        //Arrange

        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new PlayerController());
        ControllerRegistry.register(new TurnController());

        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Ramon");

        Player player = playerController.getPlayers().get(0);
        Players playerEnum = player.getPlayersEnum();

        List<OwnableLocation> ownableLocations = locationController.getOwnableLocations();
        ownableLocations.get(1).setOwner(player, true);

        //ACT
        locationController.getMortgageOnLocation(ownableLocations.get(1));
        locationController.payMortgage(ownableLocations.get(1));


        //ASSERT
        Assertions.assertFalse(ownableLocations.get(0).hasMortgage());
    }

    @Test
    public void Should_Increase_Balance_When_Mortgaging_Location() {
        //Arrange
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new PlayerController());

        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Ramon");

        Player player = playerController.getPlayers().get(0);
        Players playerEnum = player.getPlayersEnum();

        List<OwnableLocation> ownableLocations = locationController.getOwnableLocations();
        ownableLocations.get(1).setOwner(player, true);
        //ACT
        locationController.getMortgageOnLocation(ownableLocations.get(1));

        //Assert
        Assertions.assertTrue(player.getBalance() > 1500);
    }

    @Test
    public void Should_return_size_zero_when_getting_locations_owned_by_player_when_he_has_none() {
        //Arrange
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new PlayerController());

        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Ramon");

        Player player = playerController.getPlayers().get(0);
        Players playerEnum = player.getPlayersEnum();

        List<OwnableLocation> ownableLocations = locationController.getOwnableLocations();
        //ACT
        List<OwnableLocation> list = locationController.getLocationsOwnedByPlayer(player);

        //Assert
        Assertions.assertTrue(list.size()== 0);
    }
}
