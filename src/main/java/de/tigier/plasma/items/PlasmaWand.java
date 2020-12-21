package de.tigier.plasma.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.world.World;


public class PlasmaWand extends Item {
    public PlasmaWand(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient) {
            if(context.getPlayer().isSneaking()) {
                if(context.getWorld().getBlockState(context.getBlockPos()).emitsRedstonePower()){
                    context.getPlayer().sendSystemMessage(new LiteralText("§c Actively Emits Redstone"), Util.NIL_UUID);
                }else {
                    context.getPlayer().sendSystemMessage(new LiteralText(String.format("§4 Current Comparator Output: %d RU", context.getWorld().getBlockState(context.getBlockPos()).getComparatorOutput(context.getWorld(), context.getBlockPos()))), Util.NIL_UUID);
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {

        }
        return super.use(world, user, hand);
    }
}
