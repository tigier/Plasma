package de.tigier.plasma;

import de.tigier.plasma.blocks.PlasmaCrate;
import de.tigier.plasma.blocks.PlasmaCrateBlockEntity;
import de.tigier.plasma.blocks.PlasmaStone;
import de.tigier.plasma.gui.PlasmaCrateController;
import de.tigier.plasma.gui.PlasmaCrateScreen;
import de.tigier.plasma.items.PlasmaWand;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.BinomialLootTableRange;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Registry {

    //ITEMS
    public static final Item PLASMA_SHARD = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item VOID_SHARD   = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item PLASMA_WAND  = new PlasmaWand(new Item.Settings().group(ItemGroup.MATERIALS));

    //Blocks
    public static final Block PLASMA_ORE = new PlasmaStone();
    public static final Block PLASMA_CRATE = new PlasmaCrate(FabricBlockSettings.of(Material.WOOD));


    //BlockEntities
    public static BlockEntityType<PlasmaCrateBlockEntity> PLASMA_CRATE_ENTITY;
    public static ScreenHandlerType<PlasmaCrateController> PLASMA_CRATE_HANDLER;

    //Just Weird Variables
    private static final Identifier STONE_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/stone");



    public static void registerBlocks() {
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK, new Identifier(Plasma.MOD_ID, "plasma_ore"), PLASMA_ORE);
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK, new Identifier(Plasma.MOD_ID, "plasma_crate"), PLASMA_CRATE);

        PLASMA_CRATE_ENTITY = net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK_ENTITY_TYPE, "plasma:plasma_crate_entity", BlockEntityType.Builder.create(PlasmaCrateBlockEntity::new, PLASMA_CRATE).build(null));
        PLASMA_CRATE_HANDLER = ScreenHandlerRegistry.registerSimple(PlasmaCrate.ID, (syncId, inventory) -> new PlasmaCrateController(syncId, inventory, ScreenHandlerContext.EMPTY));
    }

    public static void registerItems(){


        //ITEMS
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(Plasma.MOD_ID, "plasma_shard"), PLASMA_SHARD);
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(Plasma.MOD_ID, "void_shard"), VOID_SHARD);
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(Plasma.MOD_ID, "plasma_wand"), PLASMA_WAND);
        //ITEMBLOCKS
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(Plasma.MOD_ID, "plasma_ore"), new BlockItem(PLASMA_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(Plasma.MOD_ID, "plasma_crate"), new BlockItem(PLASMA_CRATE, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

    public static void registerGui(){
        ScreenRegistry.<PlasmaCrateController, PlasmaCrateScreen>register(PLASMA_CRATE_HANDLER,(gui, inventory, title) -> new PlasmaCrateScreen(gui, inventory.player, title));
    }



    public static void registerLoot(){
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (STONE_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(new BinomialLootTableRange(1, 0.0625F))
                        .withEntry(ItemEntry.builder(PLASMA_SHARD).build())
                        .withCondition(MatchToolLootCondition.builder(ItemPredicate.Builder.create()
                                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1)))).invert()
                                .build())
                        .withCondition(MatchToolLootCondition.builder(ItemPredicate.Builder.create()
                                .enchantment(new EnchantmentPredicate(Enchantments.FORTUNE, NumberRange.IntRange.atLeast(1)))).invert()
                                .build());

                supplier.withPool(poolBuilder.build());

                for (int i=1;i<Enchantments.FORTUNE.getMaxLevel()+1;i++) {
                    poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(new BinomialLootTableRange(i, 0.0625F))
                            .withEntry(ItemEntry.builder(PLASMA_SHARD).build())
                            .withCondition(MatchToolLootCondition.builder(ItemPredicate.Builder.create()
                                    .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1)))).invert()
                                    .build())
                            .withCondition(MatchToolLootCondition.builder(ItemPredicate.Builder.create()
                                    .enchantment(new EnchantmentPredicate(Enchantments.FORTUNE, NumberRange.IntRange.exactly(i))))
                                    .build());


                    supplier.withPool(poolBuilder.build());
                }
            }
        });
    }
}
