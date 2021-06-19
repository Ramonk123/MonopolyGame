package Tests;

import Controllers.*;
import Models.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerMovementTest {

    @Test
    public void Should_HavePosition5_When_Moved5PositionsAtStartingPosition0() {
        //Arrange
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        Player player = new Player(Players.PLAYER_ONE, "Vincent");

        //Act
        player.movePlayer(5);

        //Assert
        Assertions.assertFalse(player.getPosition() == 4);
        Assertions.assertTrue(player.getPosition() == 5);
        Assertions.assertFalse(player.getPosition() == 6);
    }

    @Test
    public void Should_HavePosition10_When_Moved5PositionsTwiceAtStartingPosition0() {
        //Arrange
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        Player player = new Player(Players.PLAYER_ONE, "Vincent");

        player.movePlayer(5);
        player.movePlayer(5);

        //Assert
        Assertions.assertFalse(player.getPosition() == 9);
        Assertions.assertTrue(player.getPosition() == 10);
        Assertions.assertFalse(player.getPosition() == 11);
    }

    @Test
    public void Should_MoveTo0_When_Moved40PositionsAtStartingPosition0() {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        playerController.setPlayerWithPlayersEnum(Players.PLAYER_ONE, "Vincent");

        Player player = playerController.getPlayers().get(0);

        //Act
        player.movePlayer(40);

        //Assert
        Assertions.assertFalse(player.getPosition() == 40);
        Assertions.assertFalse(player.getPosition() == 41);
        Assertions.assertTrue(player.getPosition() == 0);

    }

    @Test
    public void Should_TeleportTo21_When_TeleportedTo21AtStartingPosition() {
        //Arrange
        ControllerRegistry.register(new TransactionController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new PlayerController());

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        Player player = new Player(Players.PLAYER_ONE, "Vincent");

        //Act
        playerController.teleportTo(player, 21);

        //Assert
        Assertions.assertFalse(player.getPosition() == 0);
        Assertions.assertTrue(player.getPosition() == 21);

    }
}
