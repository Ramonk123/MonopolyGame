package Controllers;

import Models.Board;
import Models.Location;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class LocationController implements Controller {

    private static LocationController locationController;

    public static LocationController getInstance() {
        if(locationController == null) {
            locationController = new LocationController();
        }
        return locationController;
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
