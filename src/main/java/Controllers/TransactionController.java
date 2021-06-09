package Controllers;

import Models.Player;
import Models.Wallet;
import Monopoly.UUID;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Optional;

public class TransactionController implements Controller {

    private Optional<Player> getPlayerByPlayersEnum(Players playersEnum) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return playerController.getPlayerByPlayersEnum(playersEnum);
    }

    public void setBalance(Players playersEnum, int balance) throws Exception {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(Exception::new);
        player.getWallet().setBalance(balance);
    }

    public int getBalance(Players playersEnum) throws Exception {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(Exception::new);
        return player.getWallet().getBalance();
    }

    public void addBalance(Players playersEnum, int value) throws Exception {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(Exception::new);
        player.getWallet().addBalance(value);
    }

    public void subtractBalance(Players playersEnum, int value) throws Exception {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(Exception::new);
        player.getWallet().subtractBalance(value);
    }

    public void payBalance(Players payerEnum, Players receiverEnum, int value) throws Exception {
        Player payer = getPlayerByPlayersEnum(payerEnum).orElseThrow(Exception::new);
        Player receiver = getPlayerByPlayersEnum(receiverEnum).orElseThrow(Exception::new);
        payer.getWallet().subtractBalance(value);
        receiver.getWallet().addBalance(value);
    }

}
