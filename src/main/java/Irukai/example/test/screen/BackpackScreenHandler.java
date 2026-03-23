package Irukai.example.test.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PlayerInventory playerInventory; // 플레이어 인벤토리 참조 추가 필요

    // 클라이언트에서 호출하는 생성자
    public BackpackScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    // 서버에서 호출하는 생성자
    public BackpackScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        // 나중에 등록할 ModScreens.BACKPACK_SCREEN_HANDLER를 사용합니다.
        super(ModScreens.BACKPACK_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.playerInventory = playerInventory; // 필드에 저장
        inventory.onOpen(playerInventory.player);

        // 1. 배낭 인벤토리 슬롯 추가 (3x3)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.addSlot(new Slot(inventory, col + row * 3, 62 + col * 18, 17 + row * 18));
            }
        }

        // 2. 플레이어 인벤토리 슬롯 추가 (기본 36칸)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    // 팁: 배낭 안에 배낭을 넣지 못하게 막는 로직 (매우 중요!)
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < 9) {
                // 배낭(0~8번 슬롯)에서 플레이어 인벤토리로 이동
                if (!this.insertItem(originalStack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // 플레이어 인벤토리에서 배낭으로 이동
                // 주의: 배낭 아이템 자체는 배낭 안에 넣지 못하게 방지
                if (originalStack.getItem() instanceof Irukai.example.test.item.BackpackItem) {
                    return ItemStack.EMPTY;
                }
                if (!this.insertItem(originalStack, 0, 9, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, originalStack);
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);

        ItemStack stack = player.getMainHandStack();
        if (!(stack.getItem() instanceof Irukai.example.test.item.BackpackItem)) {
            stack = player.getOffHandStack();
        }

        if (stack.getItem() instanceof Irukai.example.test.item.BackpackItem) {
            net.minecraft.nbt.NbtList nbtList = new net.minecraft.nbt.NbtList();

            for (int i = 0; i < this.inventory.size(); i++) {
                ItemStack itemStack = this.inventory.getStack(i);
                if (!itemStack.isEmpty()) {
                    net.minecraft.nbt.NbtCompound slotNbt = new net.minecraft.nbt.NbtCompound();
                    slotNbt.putByte("Slot", (byte) i);
                    itemStack.writeNbt(slotNbt);
                    nbtList.add(slotNbt);
                }
            }

            stack.getOrCreateNbt().put("Inventory", nbtList);
        }
    }
}