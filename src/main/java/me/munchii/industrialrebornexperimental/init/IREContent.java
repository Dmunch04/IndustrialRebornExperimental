package me.munchii.industrialrebornexperimental.init;

import me.munchii.industrialrebornexperimental.blockentity.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import techreborn.blocks.GenericMachineBlock;
import techreborn.blocks.generator.GenericGeneratorBlock;

import java.util.Locale;

public class IREContent {
    public static Block BROKEN_SPAWNER;

    public static Item EMPTY_SOUL_VIAL;

    public static Item FILLED_SOUL_VIAL;

    // https://github.com/TechReborn/TechReborn/blob/1.20/src/main/java/techreborn/init/TRContent.java#L783

    public enum Machine implements ItemConvertible {
        SOUL_FUSER(new GenericMachineBlock(GuiType.SOUL_FUSER, SoulFuserBlockEntity::new)),
        SOUL_INFUSER(new GenericMachineBlock(GuiType.SOUL_FUSER, SoulInfuserBlockEntity::new)),
        POWERED_SPAWNER(new GenericMachineBlock(GuiType.POWERED_SPAWNER, PoweredSpawnerBlockEntity::new)),

        DEATH_FUEL_GENERATOR(new GenericGeneratorBlock(GuiType.DEATH_GENERATOR, DeathFuelGeneratorBlockEntity::new));

        public final String name;
        public final Block block;

        <B extends Block> Machine(B block) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.block = block;
        }

        public ItemStack getStack() {
            return new ItemStack(block);
        }

        @Override
        public Item asItem() {
            return block.asItem();
        }
    }
}
