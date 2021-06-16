package Models;

/**
 * Model that contains everything that a utility location needs.
 */
public class UtilityLocation extends OwnableLocation{


    public UtilityLocation(Locations locationEnum, String name, Set set, int position, int price) {
        super(locationEnum, name, set, position, price);
    }

    @Override
    public void action(Player player) {

    }
}
