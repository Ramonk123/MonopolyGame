package Models;

/**
 * Model that contains everything that a station location needs.
 */
public class StationLocation extends OwnableLocation{

    public StationLocation(Locations locationsEnum, String name, Set set, int position, int price) {
        super(locationsEnum, name, set, position, price);
    }

    @Override
    public void action(Player player) {

    }
}
