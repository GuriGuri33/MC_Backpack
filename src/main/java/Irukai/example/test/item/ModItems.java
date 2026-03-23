package Irukai.example.test.item;


import Irukai.example.test.Test;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // 1. 아이템 객체 생성
    public static final Item BACKPACK = registerItem("backpack",
            new BackpackItem(new FabricItemSettings()));

    // 2. 실제 등록 로직
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Test.MOD_ID, name), item);
    }

    // 3. 메인 클래스에서 호출할 메서드
    public static void registerModItems() {
        Test.LOGGER.info("Registering Items for " + Test.MOD_ID);
    }
}