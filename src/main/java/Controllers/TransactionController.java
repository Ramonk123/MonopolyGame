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

    public void setBalance(Players playersEnum, int balance) throws TransactionException {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        player.getWallet().setBalance(balance);
    }

    public int getBalance(Players playersEnum) throws TransactionException {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        return player.getWallet().getBalance();
    }

    public void addBalance(Players playersEnum, int value) throws TransactionException {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        player.getWallet().addBalance(value);
    }

    public void subtractBalance(Players playersEnum, int value) throws TransactionException {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        player.getWallet().subtractBalance(value);
    }

    public void payBalance(Players payerEnum, Players receiverEnum, int value) throws TransactionException {
        Player payer = getPlayerByPlayersEnum(payerEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        Player receiver = getPlayerByPlayersEnum(receiverEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        payer.getWallet().subtractBalance(value);
        receiver.getWallet().addBalance(value);
    }

}
