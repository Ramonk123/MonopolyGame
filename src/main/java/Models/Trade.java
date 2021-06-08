package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Trade implements Model {
    private OwnableLocation selectedLocation;
    private Player biddingPlayer;
    private int biddingAmount;
}
