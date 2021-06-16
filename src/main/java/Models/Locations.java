package Models;

import Monopoly.Identifiable;
import Monopoly.UUID;

/**
 * Enum which creates new UUIDs for all locations.
 */
public enum Locations implements Identifiable {
    Dorpstraat(new UUID("LOCATION-DORPSTRAAT")),
    Brink(new UUID("LOCATION-Brink")),
    Steenstraat(new UUID("LOCATION-STEENSTRAAT")),
    Ketelstraat(new UUID("LOCATION-KETELSTRAAT")),
    Velperplein(new UUID("LOCATION-VELPERPLEIN")),
    Barteljorisstraat(new UUID("LOCATION-BARTELJORISSTRAAT")),
    Zijlweg(new UUID("LOCATION-ZIJLWEG")),
    Houtstraat(new UUID("LOCATION-HOUTSTRAAT")),
    Neude(new UUID("LOCATION-NEUDE")),
    Biltstraat(new UUID("LOCATION-BILTSTRAAT")),
    Vreeburg(new UUID("LOCATION-VREEBURG")),
    A_Kerkhof(new UUID("LOCATION-AKERKHOF")),
    GroteMarkt(new UUID("LOCATION-GROTEMARKT")),
    Herestraat(new UUID("LOCATION-HERESTRAAT")),
    Spui(new UUID("LOCATION-SPUI")),
    Plein(new UUID("LOCATION-PLEIN")),
    LangePoten(new UUID("LOCATION-LANGEPOTEN")),
    Hofplein(new UUID("LOCATION-HOFPLEIN")),
    Blaak(new UUID("LOCATION-BLAAK")),
    Coolsingel(new UUID("LOCATION-COOLSINGEL")),
    Leidsestraat(new UUID("LOCATION-LEIDSESTRAAT")),
    Kalverstraat(new UUID("LOCATION-KALVERSTRAAT")),
    ChanceOne(new UUID("LOCATION-CHANCEONE")),
    ChanceTwo(new UUID("LOCATION-CHANCETWO")),
    ChanceThree(new UUID("LOCATION-CHANCETHREE")),
    CommunityChestOne(new UUID("LOCATION-COMMUNITYCHESTONE")),
    CommunityChestTwo(new UUID("LOCATION-COMMUNITYCHESTTWO")),
    CommunityChestThree(new UUID("LOCATION-COMMUNITYCHESTTHREE")),
    StationNorth(new UUID("LOCATION-STATIONNORTH")),
    StationEast(new UUID("LOCATION-STATIONEAST")),
    StationSouth(new UUID("LOCATION-STATIONSOUTH")),
    StationWest(new UUID("LOCATION-STATIONWEST")),
    IncomeTax(new UUID("LOCATION-INCOMETAX")),
    SuperTax(new UUID("LOCATION-SUPERTAX")),
    ElectricCompany(new UUID("LOCATION-ELECTRICCOMPANY")),
    WaterWorks(new UUID("LOCATION-WATERWORKS")),
    FreeParking(new UUID("LOCATION-FREEPARKING")),
    Start(new UUID("LOCATION-START")),
    Jail(new UUID("LOCATION-JAIL")),
    GoToJail(new UUID("LOCATION-GOTOJAIL"));

    private UUID id;

    Locations(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
