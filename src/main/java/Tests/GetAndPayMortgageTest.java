package Tests;

import Controllers.*;
import Exceptions.TransactionException;
import Models.Location;
import Models.OwnableLocation;
import Models.Player;
import Models.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.List;

public class GetAndPayMortgageTest {

    @Test
    public void Should_Return_True_On_Has_Mortgage_When_Taking_A_Mortgage() throws TransactionException {
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


    }
}
