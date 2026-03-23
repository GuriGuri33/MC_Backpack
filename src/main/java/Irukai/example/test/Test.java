package Irukai.example.test;

import Irukai.example.test.item.ModItems;
import Irukai.example.test.screen.ModScreens;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test implements ModInitializer {
    public static final String MOD_ID = "test";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModScreens.registerScreens(); // 추가
    }
}