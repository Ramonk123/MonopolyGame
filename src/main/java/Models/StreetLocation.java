package Models;

import Controllers.*;
import Monopoly.UUID;

/**
 * Model that contains everything that a street location needs.
 */
public class StreetLocation extends OwnableLocation {
    private final int houses;
    private final boolean hotel;
    private final PriceInflator priceInflator;
    private final int housePrice;
    private final int hotelPrice;
    private final int rent;


    public StreetLocation(Locations locationEnum, String name, Set set, int position, int price, int rent, int housePrice, int hotelPrice) {
        super(locationEnum, name, set, position, price);
        this.houses         = 0;
        this.hotel          = false;
        this.rent           = rent;
        this.priceInflator  = new PriceInflator(rent, 1.6);
        this.housePrice     = housePrice;
        this.hotelPrice     = hotelPrice;
    }

    private boolean containsProperty() {
        return this.houses > 0 || this.hotel;
    }

    @Override
    public void action(Player player) {
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        Players players = turnController.getCurrentPlayer();
        PlayerController playerController= (PlayerController) ControllerRegistry.get(PlayerController.class);
        Player currentPlayer = playerController.getPlayerByPlayersEnum(players).orElseThrow();
        System.out.println(getOwner().isEmpty());
        System.out.println("begin van action");
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        //OwnableLocation location = (OwnableLocation) locationController.getLocationByEnum(this.getId()).orElseThrow();
            if (getOwner().isEmpty()){
                System.out.println(getOwner() + "de owner staat hier");
                System.out.println("streetlocation popup");
                Actions.buyLocationPopup(currentPlayer, getPrice(), this);
            } else if(UUID.compare(getOwner().orElseThrow(), currentPlayer)) {
            //TODO:
            // Is owned by the player standing on the location
            } else {
                    Actions.payFunds(currentPlayer, getRent()* priceInflator.inflateByTicks(getHouses()));
                    Actions.receiveFunds(getOwner().orElseThrow(), getRent()* priceInflator.inflateByTicks(getHouses()));
                    Actions.payRentPopup(player, this);
                }
            }



    public int getHouses() {
        return houses;
    }

    public boolean getHotel() {
        return hotel;
    }

    public int getRent(){ return rent;}
}
