package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Model that contains all the rules needed to make trading possible.
 */
public class Trade implements Model {
    private OwnableLocation selectedLocation;
    private Player biddingPlayer;
    private int biddingAmount;
}
