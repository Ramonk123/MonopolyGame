package Tests;

import Controllers.*;
import Exceptions.TransactionException;
import Models.Player;
import Models.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GiveAndReceiverSalaryTest {

    @Test
    public void Should_Have1700Balance_When_Added200BalanceToABalanceOf1500() throws TransactionException {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

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

    @Test
    public void Should_Get200Balance_When_PassingGoFromPosition0To40() {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Vincent");

        Player player = playerController.getPlayers().get(0);

        Wallet wallet = player.getWallet();

        //Act
        player.movePlayer(40);

        //Assert
        Assertions.assertFalse(wallet.getBalance() == 0);
        Assertions.assertFalse(wallet.getBalance() == 1500);
        Assertions.assertTrue(wallet.getBalance() == 1700);
    }

    @Test
    public void Should_Get200Balance_When_PassingGoFromPosition39To0() {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Vincent");

        Player player = playerController.getPlayers().get(0);
        playerController.teleportTo(player, 39);

        Wallet wallet = player.getWallet();

        //Act
        player.movePlayer(1);

        //Assert
        Assertions.assertFalse(wallet.getBalance() == 0);
        Assertions.assertFalse(wallet.getBalance() == 1500);
        Assertions.assertTrue(wallet.getBalance() == 1700);
    }

    @Test
    public void Should_NotGet200Balance_When_TeleportingFromPosition39To0() {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Vincent");

        Player player = playerController.getPlayers().get(0);
        playerController.teleportTo(player, 39);

        Wallet wallet = player.getWallet();

        //Act
        player.movePlayer(-39);

        //Assert
        Assertions.assertFalse(wallet.getBalance() == 0);
        Assertions.assertTrue(wallet.getBalance() == 1500);
        Assertions.assertFalse(wallet.getBalance() == 1700);
    }

    @Test
    public void Should_NotGet200Balance_When_TeleportingFromPosition0To0() {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Vincent");

        Player player = playerController.getPlayers().get(0);

        Wallet wallet = player.getWallet();

        //Act
        playerController.teleportTo(player, 0);

        //Assert
        Assertions.assertFalse(wallet.getBalance() == 0);
        Assertions.assertTrue(wallet.getBalance() == 1500);
        Assertions.assertFalse(wallet.getBalance() == 1700);
    }

    @Test
    public void Should_NotGet200Balance_When_MovingFromPosition0To0() {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Vincent");

        Player player = playerController.getPlayers().get(0);

        Wallet wallet = player.getWallet();

        //Act
        player.movePlayer(0);

        //Assert
        Assertions.assertFalse(wallet.getBalance() == 0);
        Assertions.assertTrue(wallet.getBalance() == 1500);
        Assertions.assertFalse(wallet.getBalance() == 1700);
    }
}
