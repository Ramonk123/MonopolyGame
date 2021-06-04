package Controllers;

import Models.Wallet;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class WalletController implements Controller {

    private static WalletController walletController;
    private Wallet wallet;

    private WalletController() {
        wallet = new Wallet();
    }

    public static WalletController getInstance() {
        if(walletController == null) {
            walletController = new WalletController();
        }
        return walletController;
    }

    @Override
    public void registerObserver(View v) {

    }

    @Override
    public void unregisterObserver(View v) {

    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {

    }
}
