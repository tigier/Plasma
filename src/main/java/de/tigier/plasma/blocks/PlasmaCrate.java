package de.tigier.plasma.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlasmaCrate extends Block implements BlockEntityProvider {
    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public static final Identifier ID = new Identifier("plasma", "plasma_crate");

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // You need a Block.createScreenHandlerFactory implementation that delegates to the block entity,
        // such as the one from BlockWithEntity
        if (player.getStackInHand(hand).isEmpty()) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            return ActionResult.SUCCESS;
        }

        //Clientside
        if (world.isClient) {
            return ActionResult.FAIL;
        }
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);
        if (!player.getStackInHand(hand).isEmpty()) {
            for (int i = 0; i < blockEntity.size(); i++) {
                // Check what is the first open slot and put an item from the player's hand there
                if (blockEntity.getStack(i).isEmpty()) {
                    // Put the stack the player is holding into the inventory
                    blockEntity.setStack(i, player.getStackInHand(hand).copy());
                    // Remove the stack from the player's hand
                    player.getStackInHand(hand).setCount(0);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }

    public PlasmaCrate(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new PlasmaCrateBlockEntity();
    }



}
