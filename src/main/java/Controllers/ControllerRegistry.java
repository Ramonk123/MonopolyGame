package Controllers;

import java.util.Collection;
import java.util.HashMap;

/**
 * Registers controllers so you only have one instance of each controller.
 */
public class ControllerRegistry {
    protected final static HashMap<String, Controller> controllers = new HashMap<>();

    public static void register(Controller controller) {
        String className = controller.getClass().getSimpleName();
        controllers.put(className, controller);
    }

    public static Controller get(Class<? extends Controller> controllerClass) {
        String className = controllerClass.getSimpleName();
        return controllers.get(className);
    }

    public static Collection<Controller> getCollection() {
        return controllers.values();
    }
}
