package Monopoly;

import java.util.HashMap;

public class UUID {
    private static HashMap<String, Boolean> cache = new HashMap<>();

    public UUID(String id) {
        if (cache.get(id) == null) {
            cache.put(id, true);
        } else {
            System.out.println(String.format("UUID '%s' already exists.", id));
            System.exit(69420);
        }
    }
}
