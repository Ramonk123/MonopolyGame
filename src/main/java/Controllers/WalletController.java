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

    /*@Override
    public void registerObserver(View v) {
        wallet.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        wallet.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        wallet.notifyObservers(ds);
    }*/
}
