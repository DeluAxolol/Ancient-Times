package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.item.DiamondChisel;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static void registerModItems() {
        // Blocks with Special Items
        registerItem("mard_flower", new StateModificationBlockItem(
                ModBlocks.MARD_FLOWER,
                state -> state.with(Block.AGE, 3),
                new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(32).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("mard_bulb", new BlockItem(
                ModBlocks.MARD_FLOWER,
                new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(32).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("ravenhead_seeds", new BlockItem(
                ModBlocks.RAVENHEAD_SPROUTS,
                new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(64).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        // Tools and Special Items
        registerItem("magnifying_glass", new MagnifyingGlass(
                new FabricItemSettings().maxCount(1).maxDamage(64).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("diamond_chisel", new DiamondChisel(
                new FabricItemSettings().maxCount(1).maxDamage(64).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        // Fruits and Items
        registerItem("ravenheads_fruit", new RavenheadsFruit(
                new FabricItemSettings().maxCount(16).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("rotten_ravenheads_fruit", new Item(
                new FabricItemSettings().maxCount(16).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("ravenheads_thorns", new Item(
                new FabricItemSettings().maxCount(16).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        // Spawn Eggs
        registerItem("trioclantus_spawn_egg", new SpawnEggItem(
                ModEntities.TRIOCLANTUS,
                0x835832,
                0x668639,
                new FabricItemSettings().arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("scalemouflis_spawn_egg", new SpawnEggItem(
                ModEntities.SCALEMOUFLIS,
                0x40643b,
                0xa19a5e,
                new FabricItemSettings().arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("rumoroxl_spawn_egg", new SpawnEggItem(
                ModEntities.RUMOROXL,
                0x6e1425,
                0xed767b,
                new FabricItemSettings().arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        // Boats
        registerItem("meal_boat", new ModBoatItem(
                false,
                ModBoatEntity.Type.MEAL,
                new FabricItemSettings().arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("meal_chest_boat", new ModBoatItem(
                true,
                ModBoatEntity.Type.MEAL,
                new FabricItemSettings().arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        // Signs
        registerItem("meal_sign", new SignItem(
                new FabricItemSettings().maxCount(16).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB),
                ModBlocks.MEAL_SIGN,
                ModBlocks.MEAL_WALL_SIGN
        ));

        registerItem("meal_hanging_sign", new HangingSignItem(
                ModBlocks.MEAL_HANGING_SIGN,
                ModBlocks.MEAL_WALL_HANGING_SIGN,
                new FabricItemSettings().maxCount(16).arch$tab(ModCreativeTabs.ANCIENT_TIMES_TAB)
        ));

        registerItem("creative_tab_logo", new Item(new FabricItemSettings()));
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(AncientTimes.MOD_ID, name), item);
    }

    public static class SpawnEggItem extends Item {
        private final EntityType<?> type;

        public SpawnEggItem(EntityType<?> type, int primaryColor, int secondaryColor, Settings settings) {
            super(settings);
            this.type = type;
        }
    }
}
