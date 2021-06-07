package Monopoly;

import java.util.HashMap;

public class UUID {
    private static HashMap<String, Boolean> cache = new HashMap<>();
    private String id;

    public UUID(String id) {
        if (cache.get(id) == null) {
            cache.put(id, true);
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
}
