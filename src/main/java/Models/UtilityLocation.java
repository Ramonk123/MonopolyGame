package Models;

import Controllers.ControllerRegistry;
import Controllers.LocationController;
import Controllers.TurnController;
import Monopoly.UUID;

import java.util.List;

/**
 * Model that contains everything that a utility location needs.
 */
public class UtilityLocation extends OwnableLocation {

    public UtilityLocation(Locations locationEnum, String name, Set set, int position, int price) {
        super(locationEnum, name, set, position, price);
    }

    @Override
    public void action(Player player) {
        /*
        Als je één nutsbedrijf hebt,
        bedraagt de huur x4 het aantal ogen van de worp.

        Als je beide nutsbedrijven hebt, bedraagt de huur 10x
        het aantal ogen van de worp.
         */

        if (getOwner().isEmpty()){
            Actions.buyLocationPopup(player, getPrice(), this);
        } else {
            if(UUID.compare(getOwner().orElseThrow(), player)) {
                //TODO:
                // Is owned by the player standing on the location
            } else {
                LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
                List<UtilityLocation> utilityLocationList = locationController.getUtilityLocationArray();

                TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
                long eyesThrown = turnController.getEyesThrown();

                if(UUID.compare(utilityLocationList.get(0).getOwner().orElseThrow(), utilityLocationList.get(1).getOwner().orElseThrow()) && utilityLocationList.get(0).getOwner().isPresent()) {
                    eyesThrown *= 10;
                } else {
                    eyesThrown *= 4;
                }

                Actions.payFunds(player, (int) eyesThrown);
                Actions.receiveFunds(getOwner().orElseThrow(), (int) eyesThrown);
            }
        }
    }
}
