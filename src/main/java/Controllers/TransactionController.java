package Controllers;

import Models.Player;
import Models.Wallet;
import Monopoly.UUID;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Optional;

public class TransactionController implements Controller {

    private Optional<Player> getPlayerByUUID(UUID playerUUID) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return playerController.getPlayerByUUID(playerUUID);
    }

    public void setBalance(UUID playerUUID, int balance) throws Exception {
        Player player = getPlayerByUUID(playerUUID).orElseThrow(Exception::new);
        player.getWallet().setBalance(balance);
    }

    public int getBalance(UUID playerUUID) throws Exception {
        Player player = getPlayerByUUID(playerUUID).orElseThrow(Exception::new);
        return player.getWallet().getBalance();
    }

    public void addBalance(UUID playerUUID, int value) throws Exception {
        Player player = getPlayerByUUID(playerUUID).orElseThrow(Exception::new);
        player.getWallet().addBalance(value);
    }

    public void subtractBalance(UUID playerUUID, int value) throws Exception {
        Player player = getPlayerByUUID(playerUUID).orElseThrow(Exception::new);
        player.getWallet().subtractBalance(value);
    }

    public void payBalance(UUID payerUUID, UUID receiverUUID, int value) throws Exception {
        Player payer = getPlayerByUUID(payerUUID).orElseThrow(Exception::new);
        Player receiver = getPlayerByUUID(receiverUUID).orElseThrow(Exception::new);
        payer.getWallet().subtractBalance(value);
        receiver.getWallet().addBalance(value);
    }

}
