package me.munchii.industrialrebornexperimental.capability;

import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;
import reborncore.common.util.NBTSerializable;

public class EntityStorageItem extends Item implements IEntityStorage, NBTSerializable {
    private StoredEntityData entityData = new StoredEntityData();

    public EntityStorageItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public StoredEntityData getStoredEntityData() {
        return entityData;
    }

    @Override
    public void setStoredEntityData(StoredEntityData entityData) {
        this.entityData = entityData;
    }

    public void empty() {
        entityData = new StoredEntityData();
    }

    @Override
    public @NotNull NbtCompound write() {
        return entityData.write();
    }

    @Override
    public void read(@NotNull NbtCompound nbtCompound) {
        entityData.read(nbtCompound);
    }
}
