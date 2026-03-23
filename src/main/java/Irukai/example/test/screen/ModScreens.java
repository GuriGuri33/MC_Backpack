package Irukai.example.test.screen;

import Irukai.example.test.Test;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
    public static final ScreenHandlerType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,
                    new Identifier(Test.MOD_ID, "backpack"),
                    new ScreenHandlerType<>(BackpackScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreens() {
        Test.LOGGER.info("Registering Screen Handlers for " + Test.MOD_ID);
    }
}
