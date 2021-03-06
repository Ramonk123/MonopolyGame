package Controllers;

import Exceptions.TransactionException;
import Models.Payer;
import Models.Player;
import Models.Receiver;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Wallet model with different methods to alter the balance of players.
 */
public class TransactionController implements Controller {

    private Optional<Player> getPlayerByPlayersEnum(Players playersEnum) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return playerController.getPlayerByPlayersEnum(playersEnum);
    }

    public void setBalance(Players playersEnum, int balance) throws TransactionException {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        player.getWallet().setBalance(balance);

        try{
            updateToFireStore();
        } catch(Exception ignore) {

        }
    }

    public int getBalance(Players playersEnum) throws TransactionException {
        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        return player.getWallet().getBalance();
    }

    public void addBalance(Players playersEnum, int value) throws TransactionException {
        Receiver receiver = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        receiver.addBalance(value);

        try{
            updateToFireStore();
        } catch(Exception ignore) {

        }
    }

    public void subtractBalance(Players playersEnum, int value) throws TransactionException {
        Payer payer = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        payer.subtractBalance(value);

        try{
            updateToFireStore();
        } catch(Exception ignore) {

        }

        Player player = getPlayerByPlayersEnum(playersEnum).orElseThrow();

        hasLostGame(player);

    }

    public void payBalance(Players payerEnum, Players receiverEnum, int value) throws TransactionException {
        Payer payer = getPlayerByPlayersEnum(payerEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        Receiver receiver = getPlayerByPlayersEnum(receiverEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        payer.subtractBalance(value);
        receiver.addBalance(value);

        try{
            updateToFireStore();
        } catch(Exception ignore) {

        }


        Player player = getPlayerByPlayersEnum(payerEnum).orElseThrow();

        hasLostGame(player);
    }

    public boolean checkBalance(Players playersEnum, int value) throws TransactionException{
        Payer player = getPlayerByPlayersEnum(playersEnum).orElseThrow(() -> new TransactionException("PlayerEnum NOT Found"));
        return player.getBalance() >= value;
    }

    public void updateToFireStore() {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        try {
            fireStoreController.updateAllPlayers(lobbyController.getToken(), playerController.getPlayers());
        } catch (ExecutionException | InterruptedException executionException) {
            executionException.printStackTrace();
        }
    }

    public void hasLostGame(Player player) {
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        if(turnController.hasLostTheGame(player)) {
            turnController.nextPlayerTurn();
        }
    }

}
