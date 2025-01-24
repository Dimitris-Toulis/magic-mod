package net.toulis.magic.block.wandEditor;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.toulis.magic.ModBlockEntities;
import net.toulis.magic.ModBlocks;
import net.toulis.magic.ModComponents;
import net.toulis.magic.ModTags;
import net.toulis.magic.item.MagicWand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WandEditorScreenHandler extends ForgingScreenHandler {
    private final World world;
    //private final Property invalidRecipe = Property.create();


    public WandEditorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public WandEditorScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        this(syncId, playerInventory, context, playerInventory.player.getWorld());
    }

    public WandEditorScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, World world) {
        super(ModBlockEntities.WAND_EDITOR_SCREEN_HANDLER, syncId, playerInventory, context, createForgingSlotsManager());

        this.world = world;
        //this.addProperty(this.invalidRecipe).set(0);
    }

    private static ForgingSlotsManager createForgingSlotsManager() {
        return ForgingSlotsManager.builder()
                .input(0, 25, 48, itemStack -> itemStack.getItem() instanceof MagicWand)
                .input(1, 70, 48, itemStack -> itemStack.isIn(ModTags.SPELL))
                .output(2, 126, 48)
                .build();
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isOf(ModBlocks.WAND_EDITOR);
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
        this.decrementStack(0);
        this.decrementStack(1);
    }

    private void decrementStack(int slot) {
        ItemStack itemStack = this.input.getStack(slot);
        if (!itemStack.isEmpty()) {
            itemStack.decrement(1);
            this.input.setStack(slot, itemStack);
        }
    }

    /*@Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (this.world instanceof ServerWorld) {
            boolean bl = this.getSlot(0).hasStack() && this.getSlot(1).hasStack() && !this.getSlot(this.getResultSlotIndex()).hasStack();
            this.invalidRecipe.set(bl ? 1 : 0);
        }
    }*/

    @Override
    public void updateResult() {
        if (this.world instanceof ServerWorld) {
            Item wand = this.input.getStack(0).getItem();
            if (wand instanceof MagicWand && this.input.getStack(1).isIn(ModTags.SPELL)) {
                ItemStack out = this.input.getStack(0).copy();
                List<String> spells = new ArrayList<>(out.getOrDefault(ModComponents.SPELLS_COMPONENT, new ArrayList<>()));
                if(spells.size()<((MagicWand) wand).getMaxSpells()){
                    spells.addLast(this.input.getStack(1).getItem().toString());
                    out.set(ModComponents.SPELLS_COMPONENT, Collections.unmodifiableList(spells));
                    this.output.setStack(0, out);
                }
            }
        }
        else {
            this.output.setLastRecipe(null);
            this.output.setStack(0, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    /*public boolean hasInvalidRecipe() {
        return this.invalidRecipe.get() > 0;
    }*/
}
