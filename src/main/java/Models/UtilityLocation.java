package Models;

public class UtilityLocation extends OwnableLocation{


    public UtilityLocation(Locations locationEnum, String name, Set set, int position, int price) {
        super(locationEnum, name, set, position, price);
    }

    @Override
    public void action(Player player) {

    }
}
