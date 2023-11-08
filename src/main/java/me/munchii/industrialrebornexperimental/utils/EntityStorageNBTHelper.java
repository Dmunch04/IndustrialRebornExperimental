package me.munchii.industrialrebornexperimental.utils;

import me.munchii.industrialrebornexperimental.IRENBTKeys;
import me.munchii.industrialrebornexperimental.capability.StoredEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class EntityStorageNBTHelper {
    // temporary solution until I find a better way with the capability system

    public static final String ENTITY_KEY = "Entity";

    public static boolean hasStoredEntity(ItemStack stack) {
        if (!stack.hasNbt()) {
            return false;
        }

        NbtCompound nbt = stack.getNbt();
        if (!nbt.contains(IRENBTKeys.ENTITY_STORAGE)) {
            return false;
        }

        NbtCompound entityNbt = nbt.getCompound(IRENBTKeys.ENTITY_STORAGE);
        if (!entityNbt.contains(ENTITY_KEY)) {
            return false;
        }

        return !entityNbt.getString(ENTITY_KEY).isEmpty();
    }

    public static void saveEntityData(ItemStack stack, LivingEntity entity) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound entityNbt = new NbtCompound();
        if (nbt.contains(IRENBTKeys.ENTITY_STORAGE)) {
            entityNbt = nbt.getCompound(IRENBTKeys.ENTITY_STORAGE);
        }

        entityNbt.putString(ENTITY_KEY, Registries.ENTITY_TYPE.getKey(entity.getType()).get().getValue().toString());
        nbt.put(IRENBTKeys.ENTITY_STORAGE, entityNbt);
        stack.writeNbt(nbt);
    }

    public static Optional<String> getEntityData(ItemStack stack) {
        if (!hasStoredEntity(stack)) {
            return Optional.empty();
        }

        return Optional.of(stack.getNbt().getCompound(IRENBTKeys.ENTITY_STORAGE).getString(ENTITY_KEY));
    }

    public static Optional<NbtCompound> getEntityDataCompound(ItemStack stack) {
        if (!hasStoredEntity(stack)) {
            return Optional.empty();
        }

        String id = stack.getNbt().getCompound(IRENBTKeys.ENTITY_STORAGE).getString(ENTITY_KEY);
        return Optional.of(StoredEntityData.of(new Identifier(id)).getEntityTag());
    }
}
