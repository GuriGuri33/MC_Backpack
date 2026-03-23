package Irukai.example.test.client.event;

import Irukai.example.test.networking.ModMessages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static KeyBinding backpackKey;

    public static void registerKeyBindings() {
        backpackKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.backpack.open", // 번역 키 이름
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.backpack.mod"
        ));

        registerCustomKeyInputs();
    }

    private static void registerCustomKeyInputs() {
        // 매 틱마다 실행되는 감시 로직
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (backpackKey.wasPressed()) {
                // B키가 눌렸다면 서버로 신호를 쏜다.
                ClientPlayNetworking.send(ModMessages.OPEN_BACKPACK_PACKET_ID, PacketByteBufs.create());
            }
        });
    }
}
