package Tests;

import Models.Card;
import Models.Locations;
import Models.Set;
import Models.StreetLocation;
import Monopoly.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UUIDTest {
    private static final UUID CARD_ONE_UUID = new UUID("CARD-EXAMPLE-ONE");
    private static final UUID CARD_TWO_UUID = new UUID("CARD-EXAMPLE-TWO");
    private static final UUID LOCATION_ONE_UUID = new UUID("LOCATION-EXAMPLE-ONE");

    @Test
    public void TestUuidCompare() {

        // True
        Assertions.assertTrue(UUID.compare(CARD_ONE_UUID, CARD_ONE_UUID));

        // True
        Assertions.assertTrue(UUID.compare("CARD-EXAMPLE-ONE", CARD_ONE_UUID));

        // False
        Assertions.assertFalse(UUID.compare(CARD_ONE_UUID, CARD_TWO_UUID));

        // False
        Assertions.assertFalse(UUID.compare("CARD-EXAMPLE-ONE", CARD_TWO_UUID));

        // Card implements Identifiable and thus can be compared to a UUID
        Card card = new Card(CARD_ONE_UUID, "Advance to Boardwalk", null);

        // True
        Assertions.assertTrue(UUID.compare(card, card));

        // True
        Assertions.assertTrue(UUID.compare(CARD_ONE_UUID, card));

        // True
        Assertions.assertTrue(UUID.compare("CARD-EXAMPLE-ONE", card));

        // False
        Assertions.assertFalse(UUID.compare(CARD_TWO_UUID, card));

        // Location implements Identifiable and thus can be compared to a UUID
        StreetLocation location = new StreetLocation(
                // The Locations enum is an enum that contains all existing locations with their respective UUIDs
                Locations.Amsterdam,
                "Amsterdam",
                Set.BLUE,
                0,
                0,
                0,
                0,
                0
        );

        // The Locations enum implements Identifiable and thus can be compared to other Identifiables.
        // True
        Assertions.assertTrue(UUID.compare(Locations.Amsterdam, location));

        // False
        Assertions.assertFalse(UUID.compare(card, location));
    }
}
