package Models;

public class StationLocation extends OwnableLocation{


    public StationLocation(Locations locationEnum, String name, Set set, int position, int price) {
        super(locationEnum, name, set, position, price);
    }

    @Override
    public void action(Player player) {
    }
}
