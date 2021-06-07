package Models;

public abstract class StreetLocation extends OwnableLocation {
    private int houses;
    private boolean hotel;
    private PriceInflator priceInflator;
    private Set set;
    private int housePrice;
    private int hotelPrice;

    public StreetLocation(Locations locationEnum, String name, int position, int price, Set set, int rent, int housePrice, int hotelPrice) {
        super(locationEnum, name, position, price);
        this.houses         = 0;
        this.hotel          = false;
        this.priceInflator  = new PriceInflator(rent, 1.6);
        this.set            = set;
        this.housePrice     = housePrice;
        this.hotelPrice     = hotelPrice;
    }
}
