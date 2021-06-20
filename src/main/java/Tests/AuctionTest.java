package Tests;

import Controllers.*;
import Exceptions.TransactionException;
import Models.Locations;
import Models.OwnableLocation;
import Models.Player;
import Models.Wallet;
import Monopoly.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuctionTest {

    @Test
    public void Pay_For_Auctioned_Location_Price() throws TransactionException {
        //Arrange
        ControllerRegistry.register(new LobbyController());
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());
        ControllerRegistry.register(new TurnController());

        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Vincent");

        Player player = playerController.getPlayers().get(0);
        Players playerEnum = player.getPlayersEnum();

        Wallet wallet = player.getWallet();

        //Act
        transactionController.subtractBalance(playerEnum, 220);

        //Assert
        Assertions.assertEquals(1280, wallet.getBalance());
    }

    @Test
    public void Auction_Set_Owner_Location() {
        //Arrange
        LocationController locationController = new LocationController();
        OwnableLocation location = (OwnableLocation) locationController.getLocationByEnum(Locations.A_Kerkhof.getId()).orElseThrow();
        Player player = new Player(Players.PLAYER_ONE, "TestPlayer");

        //Act
        location.setOwner(player, true);

        //Assert
        Assertions.assertTrue(UUID.compare(player, location.getOwner().get()));
    }
}
