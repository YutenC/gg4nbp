package gg.nbp.web.shop.shopproduct.common.backgroundtask;

import java.util.HashMap;
import java.util.Map;

public class BackgroundFactory extends BackgroundHandler {

    static Map<String, BackgroundHandler> backgroundHandlers = new HashMap<>();

    BackgroundFactory() {
    }

    public static BackgroundHandler getBackgroundHandler(String name) {
        BackgroundHandler backgroundHandler;
        if ((backgroundHandler = backgroundHandlers.get(name)) == null) {
            backgroundHandler = new BackgroundFactory();
            backgroundHandlers.put(name, backgroundHandler);
            new Thread(backgroundHandler).start();
        }
        return backgroundHandler;
    }

}
