package me.munchii.industrialrebornexperimental;

import me.munchii.industrialrebornexperimental.init.IREBlockEntities;
import me.munchii.industrialrebornexperimental.init.IREContent;
import me.munchii.industrialrebornexperimental.init.IREItemGroup;
import me.munchii.industrialrebornexperimental.items.SoulVialItem;
import me.munchii.industrialrebornexperimental.utils.Resources;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techreborn.init.TRBlockSettings;

import java.util.Arrays;

public class RegistryManager {
    public static void register() {
        registerBlocks();
        registerItems();

        IREBlockEntities.init();
        IREItemGroup.register();
    }

    private static void registerBlocks() {
        Arrays.stream(IREContent.Machine.values()).forEach(value -> registerBlock(value.name, value.block));

        // TODO: fix non-transparent texture
        IREContent.BROKEN_SPAWNER = registerBlock("broken_spawner", new Block(TRBlockSettings.machine()));
    }

    private static void registerItems() {
        IREContent.EMPTY_SOUL_VIAL = registerItem("empty_soul_vial", new SoulVialItem(false));
        IREContent.FILLED_SOUL_VIAL = registerItem("filled_soul_vial", new SoulVialItem(true));
    }

    private static <I extends Item> I registerItem(String name, I item) {
        Registry.register(Registries.ITEM, Resources.id(name), item);
        return item;
    }

    private static <B extends Block> B registerBlock(String name, B block) {
        return registerBlock(name, block, new Item.Settings());
    }

    private static <B extends Block> B registerBlock(String name, B block, Item.Settings itemBlockSettings) {
        Registry.register(Registries.BLOCK, Resources.id(name), block);
        Registry.register(Registries.ITEM, Resources.id(name), new BlockItem(block, itemBlockSettings));
        return block;
    }
}
