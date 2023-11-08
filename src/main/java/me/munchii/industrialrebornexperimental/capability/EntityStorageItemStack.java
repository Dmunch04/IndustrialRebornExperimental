package me.munchii.industrialrebornexperimental.capability;

import me.munchii.industrialrebornexperimental.IRENBTKeys;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class EntityStorageItemStack implements IEntityStorage {
    private final ItemStack stack;

    public EntityStorageItemStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public StoredEntityData getStoredEntityData() {
        NbtCompound nbt = stack.getOrCreateNbt();
        StoredEntityData entityData = StoredEntityData.empty();
        if (nbt.contains(BlockItem.BLOCK_ENTITY_TAG_KEY)) {
            NbtCompound entityTag = nbt.getCompound(BlockItem.BLOCK_ENTITY_TAG_KEY).getCompound(IRENBTKeys.ENTITY_STORAGE);
            entityData.read(entityTag);
        }

        return entityData;
    }

    @Override
    public void setStoredEntityData(StoredEntityData entityData) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound entityTag = new NbtCompound();
        entityTag.put(IRENBTKeys.ENTITY_STORAGE, entityData.write());
        nbt.put(BlockItem.BLOCK_ENTITY_TAG_KEY, entityTag);
    }
}
