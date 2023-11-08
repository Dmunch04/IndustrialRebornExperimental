package me.munchii.industrialrebornexperimental.capability;

public interface IEntityStorage {
    default boolean hasStoredEntity() {
        return getStoredEntityData().getEntityType().isPresent();
    }

    StoredEntityData getStoredEntityData();

    void setStoredEntityData(StoredEntityData entityData);
}
