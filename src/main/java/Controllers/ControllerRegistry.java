package Controllers;

import java.util.Collection;
import java.util.HashMap;

public class ControllerRegistry {
    protected static HashMap<String, Controller> controllers = new HashMap<>();

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
