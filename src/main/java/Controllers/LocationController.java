package Controllers;

import Exceptions.TransactionException;
import Models.*;
import Monopoly.UUID;
import javafx.geometry.Pos;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Controller for the Location, OwnableLocation and SpecialLocation model, mainly to create Lists with (all) Locations in them.
 */
public class LocationController implements Controller {
    private static LocationController locationController;
    private AuctionController auctionController = new AuctionController();
    private List<Location> locationArray = new ArrayList<>();
    private List<OwnableLocation> ownableLocationArray = new ArrayList<>();
    private List<SpecialLocation> specialLocationArray = new ArrayList<>();

    private List<StreetLocation> streetLocationArray = new ArrayList<>();
    private List<StationLocation> stationLocationArray = new ArrayList<>();
    private List<UtilityLocation> utilityLocationArray = new ArrayList<>();

    public List<StreetLocation> getStreetLocationArray() {
        return streetLocationArray;
    }

    public List<StationLocation> getStationLocationArray() {
        return stationLocationArray;
    }

    public List<UtilityLocation> getUtilityLocationArray() {
        return utilityLocationArray;
    }

    public LocationController(){
        setSpecialLocations();
        setStationLocations();
        setStreetLocations();
        setUtilityLocations();
        locationArray.sort(
                Comparator.comparingLong((ToLongFunction<Position>) Position::getPosition)
        );
    }


    /*@Override
    public void registerObserver(View v) {

    }

    @Override
    public void unregisterObserver(View v) {

    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {

    }*/

    public List<Location> getLocationArray() {
        return locationArray;
    }

    //TODO: Create onEvent function when refusing to buy location
    private void refuseToBuyLocation() {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        for(Player player : playerController.getPlayers()){
            if(UUID.compare(playerController.getClientPlayersEnum().getId(), player.getId())){
                long position = player.getPosition();
                String positionId = locationArray.get((int) position).getId().getId();
                auctionController.startAuction(positionId);
            }
        }
    }

    /**
     * Creates and adds all street locations to the locationArray and ownableLocationArray.
     */
    public void setStreetLocations(){
        StreetLocation dorpStraat = new StreetLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.Dorpstraat,
                "Dorpstraat",
                Set.BROWN,
                1,
                100,
                0,
                0,
                0
        );
        StreetLocation brink = new StreetLocation(
                Locations.Brink,
                "Brink",
                Set.BROWN,
                3,
                0,
                0,
                0,
                0
        );
        StreetLocation steenStraat = new StreetLocation(
                Locations.Steenstraat,
                "Steenstraat",
                Set.LIGHTBLUE,
                6,
                125,
                0,
                0,
                0
        );
        StreetLocation ketelStraat = new StreetLocation(
                Locations.Ketelstraat,
                "Ketelstraat",
                Set.LIGHTBLUE,
                8,
                0,
                0,
                0,
                0
        );
        StreetLocation velperPlein = new StreetLocation(
                Locations.Velperplein,
                "Velperplein",
                Set.LIGHTBLUE,
                9,
                0,
                0,
                0,
                0
        );
        StreetLocation bartelJorisStraat = new StreetLocation(
                Locations.Barteljorisstraat,
                "Barteljorisstraat",
                Set.PINK,
                11,
                0,
                0,
                0,
                0
        );
        StreetLocation zijlWeg = new StreetLocation(
                Locations.Zijlweg,
                "Zijlweg",
                Set.PINK,
                13,
                0,
                0,
                0,
                0
        );
        StreetLocation houtStraat = new StreetLocation(
                Locations.Houtstraat,
                "Houtstraat",
                Set.PINK,
                14,
                0,
                0,
                0,
                0
        );
        StreetLocation neude = new StreetLocation(
                Locations.Neude,
                "Neude",
                Set.ORANGE,
                16,
                0,
                0,
                0,
                0
        );
        StreetLocation biltStraat = new StreetLocation(
                Locations.Biltstraat,
                "Biltstraat",
                Set.ORANGE,
                18,
                0,
                0,
                0,
                0
        );
        StreetLocation vreeBurg = new StreetLocation(
                Locations.Vreeburg,
                "Vreeburg",
                Set.ORANGE,
                19,
                0,
                0,
                0,
                0
        );
        StreetLocation aKerkhof = new StreetLocation(
                Locations.A_Kerkhof,
                "A-Kerkhof",
                Set.RED,
                21,
                0,
                0,
                0,
                0
        );
        StreetLocation groteMarkt = new StreetLocation(
                Locations.GroteMarkt,
                "Grote Markt",
                Set.RED,
                23,
                0,
                0,
                0,
                0
        );
        StreetLocation hereStraat = new StreetLocation(
                Locations.Herestraat,
                "Herestraat",
                Set.RED,
                24,
                0,
                0,
                0,
                0
        );
        StreetLocation spui = new StreetLocation(
                Locations.Spui,
                "Spui",
                Set.YELLOW,
                26,
                0,
                0,
                0,
                0
        );
        StreetLocation plein = new StreetLocation(
                Locations.Plein,
                "Plein",
                Set.YELLOW,
                27,
                0,
                0,
                0,
                0
        );
        StreetLocation langePoten = new StreetLocation(
                Locations.LangePoten,
                "Lange Poten",
                Set.YELLOW,
                29,
                0,
                0,
                0,
                0
        );
        StreetLocation hofplein = new StreetLocation(
                Locations.Hofplein,
                "Hofplein",
                Set.GREEN,
                31,
                0,
                0,
                0,
                0
        );
        StreetLocation blaak = new StreetLocation(
                Locations.Blaak,
                "Blaak",
                Set.GREEN,
                32,
                0,
                0,
                0,
                0
        );
        StreetLocation coolSingel = new StreetLocation(
                Locations.Coolsingel,
                "Coolsingel",
                Set.GREEN,
                34,
                0,
                0,
                0,
                0
        );
        StreetLocation leidseStraat = new StreetLocation(
                Locations.Leidsestraat,
                "Leidsestraat",
                Set.DARKBLUE,
                37,
                0,
                0,
                0,
                0
        );
        StreetLocation kalverStraat = new StreetLocation(
                Locations.Kalverstraat,
                "Kalverstraat",
                Set.DARKBLUE,
                39,
                0,
                0,
                0,
                0
        );
        locationArray.add(dorpStraat);
        locationArray.add(brink);
        locationArray.add(steenStraat);
        locationArray.add(ketelStraat);
        locationArray.add(velperPlein);
        locationArray.add(bartelJorisStraat);
        locationArray.add(zijlWeg);
        locationArray.add(houtStraat);
        locationArray.add(neude);
        locationArray.add(biltStraat);
        locationArray.add(vreeBurg);
        locationArray.add(aKerkhof);
        locationArray.add(groteMarkt);
        locationArray.add(hereStraat);
        locationArray.add(spui);
        locationArray.add(plein);
        locationArray.add(langePoten);
        locationArray.add(hofplein);
        locationArray.add(blaak);
        locationArray.add(coolSingel);
        locationArray.add(leidseStraat);
        locationArray.add(kalverStraat);

        streetLocationArray.add(dorpStraat);
        streetLocationArray.add(brink);
        streetLocationArray.add(steenStraat);
        streetLocationArray.add(ketelStraat);
        streetLocationArray.add(velperPlein);
        streetLocationArray.add(bartelJorisStraat);
        streetLocationArray.add(zijlWeg);
        streetLocationArray.add(houtStraat);
        streetLocationArray.add(neude);
        streetLocationArray.add(biltStraat);
        streetLocationArray.add(vreeBurg);
        streetLocationArray.add(aKerkhof);
        streetLocationArray.add(groteMarkt);
        streetLocationArray.add(hereStraat);
        streetLocationArray.add(spui);
        streetLocationArray.add(plein);
        streetLocationArray.add(langePoten);
        streetLocationArray.add(hofplein);
        streetLocationArray.add(blaak);
        streetLocationArray.add(coolSingel);
        streetLocationArray.add(leidseStraat);
        streetLocationArray.add(kalverStraat);

        ownableLocationArray.add(dorpStraat);
        ownableLocationArray.add(brink);
        ownableLocationArray.add(steenStraat);
        ownableLocationArray.add(ketelStraat);
        ownableLocationArray.add(velperPlein);
        ownableLocationArray.add(bartelJorisStraat);
        ownableLocationArray.add(zijlWeg);
        ownableLocationArray.add(houtStraat);
        ownableLocationArray.add(neude);
        ownableLocationArray.add(biltStraat);
        ownableLocationArray.add(vreeBurg);
        ownableLocationArray.add(aKerkhof);
        ownableLocationArray.add(groteMarkt);
        ownableLocationArray.add(hereStraat);
        ownableLocationArray.add(spui);
        ownableLocationArray.add(plein);
        ownableLocationArray.add(langePoten);
        ownableLocationArray.add(hofplein);
        ownableLocationArray.add(blaak);
        ownableLocationArray.add(coolSingel);
        ownableLocationArray.add(leidseStraat);
        ownableLocationArray.add(kalverStraat);

    }

    /**
     * Creates and adds all special locations to the locationArray and specialLocationArray.
     */
    public void setSpecialLocations(){
        SpecialLocation start = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.Start,
                "Start",
                Set.NONE,
                0,
                Actions::exampleAction
        );
        SpecialLocation jail = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.Jail,
                "Gevangenis",
                Set.NONE,
                10,
                Actions::exampleAction

        );
        SpecialLocation goToJail = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.GoToJail,
                "Naar de Gevangenis",
                Set.NONE,
                30,
                Actions::exampleAction

        );
        SpecialLocation incomeTax = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.IncomeTax,
                "Inkomsten Belasting",
                Set.NONE,
                4,
                Actions::exampleAction

        );
        SpecialLocation superTax = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.SuperTax,
                "Extra Belasting",
                Set.NONE,
                38,
                Actions::exampleAction

        );
        SpecialLocation chanceOne = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.ChanceOne,
                "Kans",
                Set.NONE,
                7,
                Actions::chanceCard

        );
        SpecialLocation chanceTwo = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.ChanceTwo,
                "Kans",
                Set.NONE,
                22,
                Actions::chanceCard

        );
        SpecialLocation chanceThree = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.ChanceThree,
                "Kans",
                Set.NONE,
                36,
                Actions::chanceCard

        );
        SpecialLocation communityChestOne = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.CommunityChestOne,
                "Algemeen Fonds",
                Set.NONE,
                2,
                Actions::exampleAction

        );
        SpecialLocation communityChestTwo = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.CommunityChestTwo,
                "Algemeen Fonds",
                Set.NONE,
                17,
                Actions::exampleAction

        );
        SpecialLocation communityChestThree = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.CommunityChestThree,
                "Algemeen Fonds",
                Set.NONE,
                33,
                Actions::exampleAction

        );
        SpecialLocation freeParking = new SpecialLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.FreeParking,
                "Vrij Parkeren",
                Set.NONE,
                20,
                Actions::exampleAction

        );
        locationArray.add(start);
        locationArray.add(jail);
        locationArray.add(goToJail);
        locationArray.add(incomeTax);
        locationArray.add(superTax);
        locationArray.add(chanceOne);
        locationArray.add(chanceTwo);
        locationArray.add(chanceThree);
        locationArray.add(communityChestOne);
        locationArray.add(communityChestTwo);
        locationArray.add(communityChestThree);
        locationArray.add(freeParking);
        specialLocationArray.add(start);
        specialLocationArray.add(jail);
        specialLocationArray.add(goToJail);
        specialLocationArray.add(incomeTax);
        specialLocationArray.add(superTax);
        specialLocationArray.add(chanceOne);
        specialLocationArray.add(chanceTwo);
        specialLocationArray.add(chanceThree);
        specialLocationArray.add(communityChestOne);
        specialLocationArray.add(communityChestTwo);
        specialLocationArray.add(communityChestThree);
        specialLocationArray.add(freeParking);

    }

    /**
     * Creates and adds all stations locations to the locationArray and ownableLocationArray.
     */
    public void setStationLocations(){
        StationLocation stationNorth = new StationLocation(
                Locations.StationNorth,
                "Station Noord",
                Set.TRAINSTATION,
                25,
                0
        );
        StationLocation stationEast = new StationLocation(
                Locations.StationEast,
                "Station Oost",
                Set.TRAINSTATION,
                35,
                0
        );
        StationLocation stationSouth = new StationLocation(
                Locations.StationSouth,
                "Station Zuid",
                Set.TRAINSTATION,
                5,
                0
        );
        StationLocation stationWest = new StationLocation(
                Locations.StationWest,
                "Station West",
                Set.TRAINSTATION,
                15,
                250
        );
        locationArray.add(stationNorth);
        locationArray.add(stationEast);
        locationArray.add(stationSouth);
        locationArray.add(stationWest);

        stationLocationArray.add(stationNorth);
        stationLocationArray.add(stationEast);
        stationLocationArray.add(stationSouth);
        stationLocationArray.add(stationWest);

        ownableLocationArray.add(stationNorth);
        ownableLocationArray.add(stationEast);
        ownableLocationArray.add(stationSouth);
        ownableLocationArray.add(stationWest);
    }

    /**
     * Creates and adds all utility locations to the locationArray and ownableLocationArray.
     */
    public void setUtilityLocations(){
        UtilityLocation waterWorks = new UtilityLocation(
                Locations.WaterWorks,
                "Waterleiding",
                Set.TRAINSTATION,
                28,
                0
        );
        UtilityLocation electricComapany = new UtilityLocation(
                Locations.ElectricCompany,
                "Elektriciteitsbedrijf",
                Set.TRAINSTATION,
                12,
                0
        );
        locationArray.add(waterWorks);
        locationArray.add(electricComapany);

        utilityLocationArray.add(waterWorks);
        utilityLocationArray.add(electricComapany);

        ownableLocationArray.add(waterWorks);
        ownableLocationArray.add(electricComapany);
    }

    public List<OwnableLocation> getOwnableLocations() {
        return ownableLocationArray;
    }

    public List<StreetLocation> getStreetLocationsOwnedByPlayer(UUID uuid) {
        List<OwnableLocation> streetLocations = new ArrayList<>();
        List<StreetLocation> playerOwnedStreetLocations = new ArrayList<>();

        for(OwnableLocation location : ownableLocationArray) {
            Set locationSet = location.getSet();
            if(!UUID.compare(locationSet,Set.TRAINSTATION) && UUID.compare(locationSet,Set.UTILITY) && UUID.compare(locationSet,Set.NONE)){
                streetLocations.add(location);
            }
        }

        for(OwnableLocation location : streetLocations) {
            if (location.getOwner().equals(uuid)){
                playerOwnedStreetLocations.add((StreetLocation) location);
            }
        }
        return playerOwnedStreetLocations;
    }

    public List<OwnableLocation> getRailroadLocations() {
        List<OwnableLocation> railRoadLocationsArray = new ArrayList<>();
        for(OwnableLocation location : ownableLocationArray) {
            if(location.getSet().equals(Set.TRAINSTATION)) {
                railRoadLocationsArray.add(location);
            }
        }return railRoadLocationsArray;
    }

    public List<OwnableLocation> getUtilityLocations() {
        List<OwnableLocation> utilityLocationsArray = new ArrayList<>();
        for(OwnableLocation location : ownableLocationArray) {
            if(location.getSet().equals(Set.UTILITY)) {
                utilityLocationsArray.add(location);
            }
        }return utilityLocationsArray;
    }


    public void getMortgageOnLocation(OwnableLocation location) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        try {
            transactionController.addBalance(playerController.getClientPlayersEnum(), location.getMortgageValue());
            location.setMortgage(true);
            System.out.println(transactionController.getBalance(playerController.getClientPlayersEnum()));
        } catch (TransactionException transactionException) {
            transactionException.printStackTrace();
        }


    }

    public void payMortgage(OwnableLocation location) {
        TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        try {
            transactionController.subtractBalance(playerController.getClientPlayersEnum(),location.getMortgageValue());
            location.setMortgage(false);
        } catch (TransactionException transactionException) {
            transactionException.printStackTrace();
        }
    }

}
