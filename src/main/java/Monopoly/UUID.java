package Monopoly;

import Models.Set;

import java.util.HashMap;

/**
 * Custom UUID class that makes Players, Cards and Locations identifiable by an ID; and has methods to compare different IDs.
 */
public class UUID {
    private static HashMap<String, Boolean> cache = new HashMap<>();
    private String id;

    public UUID(String id) {
        if (cache.putIfAbsent(id, true) == null) {
            this.id = id;
        } else {
            System.out.println(String.format("UUID '%s' already exists.", id));
            System.exit(69420);
        }
    }

    public String getId() { return id; }

    public static boolean compare(UUID idOne, UUID idTwo) {
        return idOne == idTwo;
    }

    public static boolean compare(String idOne, UUID idTwo) {
        return idOne.equals(idTwo.getId());
    }

    public static boolean compare(UUID idOne, Identifiable idTwo) {
        return idOne == idTwo.getId();
    }

    public static boolean compare(Identifiable idOne, Identifiable idTwo) {
        return idOne.getId() == idTwo.getId();
    }

    public static boolean compare(String idOne, Identifiable idTwo) {
        return idOne.equals(idTwo.getId().getId());
    }

    public static boolean compare(Set setOne, Set setTwo) {
        return setOne.equals(setTwo);
    }
}
