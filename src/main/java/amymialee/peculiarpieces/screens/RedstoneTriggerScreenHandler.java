package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class RedstoneTriggerScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public RedstoneTriggerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2));
    }

    public RedstoneTriggerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(PeculiarPieces.REDSTONE_TRIGGER_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        for(int j = 0; j < 2; ++j) {
            this.addSlot(new Slot(inventory, j, 71 + (j * 18), 20) {
                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.isOf(PeculiarItems.REDSTONE_REMOTE) ||
                            stack.isOf(PeculiarItems.REACHING_REMOTE) ||
                            stack.isOf(PeculiarItems.POS_PAPER) ||
                            stack.isOf(PeculiarItems.POS_TRAP) ||
                            stack.isOf(PeculiarItems.POS_PEARL);
                }
            });
        }
        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 51));
            }
        }
        for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack = slot.getStack();
            copy = itemStack.copy();
            if (index < 2 ? !this.insertItem(itemStack, 2, this.slots.size(), true) : !this.insertItem(itemStack, 0, 2, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return copy;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
}