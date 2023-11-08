package me.munchii.industrialrebornexperimental.init;

import me.munchii.industrialrebornexperimental.utils.Resources;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class IREItemGroup {
    private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Resources.id("item_group"));

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.industrialrebornexperimental.item_group"))
                .icon(() -> new ItemStack(IREContent.FILLED_SOUL_VIAL))
                .build());

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(IREItemGroup::entries);
    }

    private static void entries(FabricItemGroupEntries entries) {
        entries.add(IREContent.EMPTY_SOUL_VIAL);

        addContent(IREContent.Machine.values(), entries);

        entries.add(IREContent.BROKEN_SPAWNER);
    }

    private static void addContent(ItemConvertible[] items, FabricItemGroupEntries entries) {
        for (ItemConvertible item : items) {
            entries.add(item);
        }
    }
}
