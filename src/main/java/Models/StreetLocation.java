package Models;

public abstract class StreetLocation extends OwnableLocation {
    public int houses;
    public boolean hotel;
    public PriceInflator priceInflator;

    public StreetLocation(String name, int position, int price, int priceOfHouse) {
        super(name, position, price);
        this.houses = 0;
        this.hotel = false;
        this.priceInflator = new PriceInflator(priceOfHouse, 0.6);
    }
}
