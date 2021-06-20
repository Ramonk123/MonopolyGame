package Tests;

import Controllers.CardDeckController;
import Controllers.ControllerRegistry;
import Controllers.ThrowController;
import Controllers.TurnController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class diceTest {

    @Test
    public void Should_Get_Random_Dice_Thrown_AndNotBeLargerThan12() {
        //Arrange

        ControllerRegistry.register(new ThrowController());
        ThrowController throwController = (ThrowController) ControllerRegistry.get(ThrowController.class);

        //Act

        throwController.throwDice();

        //Assert

        Assertions.assertFalse(throwController.getEyesDiceOne() + throwController.getEyesDiceTwo() > 12);

    }

}
