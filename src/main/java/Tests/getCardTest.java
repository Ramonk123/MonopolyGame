package Tests;

import Controllers.CardDeckController;
import Controllers.ControllerRegistry;
import Controllers.LocationController;
import Controllers.PlayerController;
import Models.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class getCardTest {

    @Test
    public void Should_Receive_RandomCard_When_Asked() {
        //Arrange

        ControllerRegistry.register(new CardDeckController());
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);

        //Act

        Card card = cardDeckController.getRandomChanceCard();

        //Assert

        Assertions.assertTrue(card != null);
    }



}
