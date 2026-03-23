package Irukai.example.test.networking;

import Irukai.example.test.Test;
import Irukai.example.test.item.BackpackItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier OPEN_BACKPACK_PACKET_ID = new Identifier(Test.MOD_ID, "open_backpack");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(OPEN_BACKPACK_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                // 서버쪽 로직: 플레이어 인벤토리를 뒤져서 가방을 찾아 열어줌.
                for (int i = 0; i < player.getInventory().size(); ++i) {
                    ItemStack stack = player.getInventory().getStack(i);
                    if (stack.getItem() instanceof BackpackItem) {
                        // 가방 아이템의 기능을 강제로 실행 (창 열기)
                        player.openHandledScreen((BackpackItem) stack.getItem());
                        return; // 가방 하나만 열고 종료
                    }
                }
            });
        });
    }
}

