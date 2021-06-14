package Resetter;

import java.util.ArrayList;
import java.util.List;

public class GameResetter {
    private static List<Resettable> resettables = new ArrayList<>();

    public static void attachResettable(Resettable resettable) {
        resettables.add(resettable);
    }

    public static void reset() {
        for (Resettable resettable : resettables) {
            resettable.reset();
        }
    }
}
