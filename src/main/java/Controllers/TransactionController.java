package Controllers;

import Models.Player;
import Models.Wallet;
import Monopoly.UUID;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class TransactionController implements Controller {

    private Player getPlayerByUUID(UUID playerUUID) {
        PlayerController pc = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return pc.getPlayerByUUID(playerUUID);
    }

    public void setBalance(UUID playerUUID, int balance) {
        Player player = getPlayerByUUID(playerUUID);
        player.getWallet().setBalance(balance);
    }

    public int getBalance(UUID playerUUID) {
        Player player = getPlayerByUUID(playerUUID);
        return player.getWallet().getBalance();
    }

    public void addBalance(UUID playerUUID, int value) {
        Player player = getPlayerByUUID(playerUUID);
        player.getWallet().addBalance(value);
    }

    public void subtractBalance(UUID playerUUID, int value) {
        Player player = getPlayerByUUID(playerUUID);
        player.getWallet().subtractBalance(value);
    }

    public void payBalance(UUID payerUUID, UUID receiverUUID, int value) {
        Player payer = getPlayerByUUID(payerUUID);
        Player receiver = getPlayerByUUID(receiverUUID);
        payer.getWallet().subtractBalance(value);
        receiver.getWallet().addBalance(value);
    }

}
