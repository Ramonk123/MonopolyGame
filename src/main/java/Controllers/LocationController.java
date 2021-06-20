package Controllers;

import Exceptions.TransactionException;
import Models.*;
import Models.Set;
import Monopoly.Main;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.*;
import java.util.function.ToLongFunction;

/**
 * Controller for the Location, OwnableLocation and SpecialLocation model, mainly to create Lists with (all) Locations in them.
 */
public class LocationController implements Controller, Subject<DocumentSnapshot>, Observer<DocumentSnapshot> {
    private static LocationController locationController;
    private final AuctionController auctionController = new AuctionController();
    private List<Location> locationArray = new ArrayList<>();
    private final List<OwnableLocation> ownableLocationArray = new ArrayList<>();
    private final List<SpecialLocation> specialLocationArray = new ArrayList<>();

    private final List<StreetLocation> streetLocationArray = new ArrayList<>();
    private final List<StationLocation> stationLocationArray = new ArrayList<>();
    private final List<UtilityLocation> utilityLocationArray = new ArrayList<>();

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
    public Optional<Location> getLocationByEnum(UUID locationId) {
        Location location = null;
        for (Location l : locationArray) {
            if (UUID.compare(l.getId(), locationId)) {
                location = l;
                break;
            }
        }
        return Optional.ofNullable(location);
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

    // Called from BoardController by auction button.
    public void refuseToBuyLocation() {
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
                60,
                2,
                50,
                250
        );
        StreetLocation brink = new StreetLocation(
                Locations.Brink,
                "Brink",
                Set.BROWN,
                3,
                60,
                4,
                50,
                450
        );
        StreetLocation steenStraat = new StreetLocation(
                Locations.Steenstraat,
                "Steenstraat",
                Set.LIGHTBLUE,
                6,
                100,
                6,
                50,
                550
        );
        StreetLocation ketelStraat = new StreetLocation(
                Locations.Ketelstraat,
                "Ketelstraat",
                Set.LIGHTBLUE,
                8,
                100,
                6,
                50,
                550
        );
        StreetLocation velperPlein = new StreetLocation(
                Locations.Velperplein,
                "Velperplein",
                Set.LIGHTBLUE,
                9,
                120,
                8,
                50,
                600
        );
        StreetLocation bartelJorisStraat = new StreetLocation(
                Locations.Barteljorisstraat,
                "Barteljorisstraat",
                Set.PINK,
                11,
                140,
                10,
                100,
                750
        );
        StreetLocation zijlWeg = new StreetLocation(
                Locations.Zijlweg,
                "Zijlweg",
                Set.PINK,
                13,
                140,
                10,
                100,
                750
        );
        StreetLocation houtStraat = new StreetLocation(
                Locations.Houtstraat,
                "Houtstraat",
                Set.PINK,
                14,
                160,
                12,
                100,
                900
        );
        StreetLocation neude = new StreetLocation(
                Locations.Neude,
                "Neude",
                Set.ORANGE,
                16,
                180,
                14,
                100,
                950
        );
        StreetLocation biltStraat = new StreetLocation(
                Locations.Biltstraat,
                "Biltstraat",
                Set.ORANGE,
                18,
                180,
                14,
                100,
                950
        );
        StreetLocation vreeBurg = new StreetLocation(
                Locations.Vreeburg,
                "Vreeburg",
                Set.ORANGE,
                19,
                200,
                16,
                100,
                1000
        );
        StreetLocation aKerkhof = new StreetLocation(
                Locations.A_Kerkhof,
                "A-Kerkhof",
                Set.RED,
                21,
                220,
                18,
                150,
                1050
        );
        StreetLocation groteMarkt = new StreetLocation(
                Locations.GroteMarkt,
                "Grote Markt",
                Set.RED,
                23,
                220,
                18,
                150,
                1050
        );
        StreetLocation hereStraat = new StreetLocation(
                Locations.Herestraat,
                "Herestraat",
                Set.RED,
                24,
                240,
                20,
                150,
                1100
        );
        StreetLocation spui = new StreetLocation(
                Locations.Spui,
                "Spui",
                Set.YELLOW,
                26,
                260,
                22,
                150,
                1150
        );
        StreetLocation plein = new StreetLocation(
                Locations.Plein,
                "Plein",
                Set.YELLOW,
                27,
                260,
                22,
                150,
                1150
        );
        StreetLocation langePoten = new StreetLocation(
                Locations.LangePoten,
                "Lange Poten",
                Set.YELLOW,
                29,
                280,
                24,
                150,
                1200
        );
        StreetLocation hofplein = new StreetLocation(
                Locations.Hofplein,
                "Hofplein",
                Set.GREEN,
                31,
                300,
                26,
                200,
                1275
        );
        StreetLocation blaak = new StreetLocation(
                Locations.Blaak,
                "Blaak",
                Set.GREEN,
                32,
                300,
                26,
                130,
                1275
        );
        StreetLocation coolSingel = new StreetLocation(
                Locations.Coolsingel,
                "Coolsingel",
                Set.GREEN,
                34,
                320,
                28,
                200,
                1400
        );
        StreetLocation leidseStraat = new StreetLocation(
                Locations.Leidsestraat,
                "Leidsestraat",
                Set.DARKBLUE,
                37,
                350,
                35,
                200,
                1500
        );
        StreetLocation kalverStraat = new StreetLocation(
                Locations.Kalverstraat,
                "Kalverstraat",
                Set.DARKBLUE,
                39,
                400,
                50,
                200,
                2000
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
                Set.UTILITY,
                28,
                0
        );
        UtilityLocation electricComapany = new UtilityLocation(
                Locations.ElectricCompany,
                "Elektriciteitsbedrijf",
                Set.UTILITY,
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

    public List<OwnableLocation> getLocationsOwnedByPlayer(Player player) {
        List<OwnableLocation> locations = new ArrayList<>();
        System.out.println("PlayerUUID" + player.getId());
        for(OwnableLocation location: ownableLocationArray) {
            if (location.getOwner().isPresent()) {
            System.out.println("Location owner name : " + location.getOwner().orElseThrow().getName());
                    System.out.println("hier kom ik nog wel");
                    System.out.println("Player name: " + player.getName());
                    if(UUID.compare(location.getOwner().orElseThrow(), player)) {
                        locations.add(location);
                    }
                }
        }
        return locations;
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

    public void testPrintLocationsOwned() {
        for (OwnableLocation location: ownableLocationArray) {
            if (location.getOwner().isPresent()) {
                System.out.println("Housa Wousa: " + location.getName() + " " + location.getOwner());
            }
        }
    }


    public HashMap<String, String> getFirestoreFormat() {
        HashMap<String, String> locationMap = new HashMap<>();
        for(OwnableLocation location : ownableLocationArray) {
            if(location.getOwner().isEmpty()){
                locationMap.put(location.getId().getId(), null);
            }else {
                locationMap.put(location.getId().getId(), location.getOwner().get().getId().getId());
            }
        }
        return locationMap;
    }


    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void update(DocumentSnapshot state) {
//        this.locationArray = (ArrayList<Location>) state.get("locations");
        HashMap<String, String> locationMap = (HashMap<String, String>) state.get("locations");
        Iterator iterator = locationMap.entrySet().iterator();
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        while(iterator.hasNext()){
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            String locationString = (String) mapEntry.getKey();
            String playerString = (String) mapEntry.getValue();
            for(OwnableLocation ownableLocation : ownableLocationArray){
                if(UUID.compare(locationString, ownableLocation.getId())){
                    ArrayList<Player> playerList = playerController.getPlayers();
                    for(int i = 0; playerList.size() > i; i++){
                        Player owner = playerList.get(i);
                        if(UUID.compare(playerString, owner)){
                            System.out.println(owner);
                            System.out.println(ownableLocation.getName());
                            ownableLocation.setOwner(owner, true);
                        }
                    }
                }
            }
        }

    }
}
