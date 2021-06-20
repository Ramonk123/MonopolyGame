package Tests;

import Controllers.*;
import Exceptions.TransactionException;
import Models.Locations;
import Models.OwnableLocation;
import Models.Player;
import Models.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TakeRent {

    @Test
    public void Receive_Rent_From_Player() throws TransactionException {
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
        transactionController.addBalance(playerEnum, 200);


        //Assert
        Assertions.assertFalse(wallet.getBalance() == 0);
        Assertions.assertFalse(wallet.getBalance() == 1500);
        Assertions.assertTrue(wallet.getBalance() == 1700);
    }
}
