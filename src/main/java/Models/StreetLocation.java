package Models;

import Controllers.ControllerRegistry;
import Controllers.LocationController;

import java.util.List;

/**
 * Model that contains everything that a street location needs.
 */
public class StreetLocation extends OwnableLocation {
    private int houses;
    private boolean hotel;
    private PriceInflator priceInflator;
    private int housePrice;
    private int hotelPrice;


    public StreetLocation(Locations locationEnum, String name, Set set, int position, int price, int rent, int housePrice, int hotelPrice) {
        super(locationEnum, name, set, position, price);
        this.houses         = 0;
        this.hotel          = false;
        this.priceInflator  = new PriceInflator(rent, 1.6);
        this.housePrice     = housePrice;
        this.hotelPrice     = hotelPrice;
    }

    private boolean containsProperty() {
        return this.houses > 0 || this.hotel;
    }

    @Override
    public void action(Player player) {
        System.out.println(getOwner().isEmpty());
        System.out.println("begin van action");
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        //OwnableLocation location = (OwnableLocation) locationController.getLocationByEnum(this.getId()).orElseThrow();
            if (getOwner().isEmpty()){
                System.out.println("streetlocation popup");
                Actions.buyLocationPopup(player, getPrice(), this);
            }
    }

    public int getHouses() {
        return houses;
    }

    public boolean getHotel() {
        return hotel;
    }
}
