package Irukai.example.test.item;

import Irukai.example.test.screen.BackpackScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item implements NamedScreenHandlerFactory {
    public BackpackItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        System.out.println("로그: use 메서드 진입됨! (서버/클라이언트 모두)"); // 이 로그가 찍히는지 보세요.

        if (!world.isClient) {
            System.out.println("서버: 배낭 열기 시도!"); // 로그 추가
            user.openHandledScreen(this); // 서버에서 화면 열기
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("나의 배낭");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        // 1. 현재 손에 들고 있는 배낭 아이템을 가져옴
        ItemStack stack = player.getStackInHand(player.getActiveHand());

        // 2. 9칸짜리 가상 인벤토리 생성
        SimpleInventory inventory = new SimpleInventory(9);

        // 3. 아이템의 NBT에서 데이터를 읽어와 인벤토리에 채움
        if (stack.hasNbt() && stack.getNbt().contains("Inventory", 9)) {
            net.minecraft.nbt.NbtList nbtList = stack.getNbt().getList("Inventory", 10); // 10은 Compound 타입
            // 반복문을 통해 슬롯에 아이템 채우기
            for (int i = 0; i < nbtList.size(); i++) {
                net.minecraft.nbt.NbtCompound slotNbt = nbtList.getCompound(i);
                int slotIndex = slotNbt.getByte("Slot") & 255;
                if (slotIndex >= 0 && slotIndex < inventory.size()) {
                    inventory.setStack(slotIndex, ItemStack.fromNbt(slotNbt));
                }
            }
        }

        return new BackpackScreenHandler(syncId, playerInventory, inventory);
    }

}