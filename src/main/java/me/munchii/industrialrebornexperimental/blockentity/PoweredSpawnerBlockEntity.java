package me.munchii.industrialrebornexperimental.blockentity;

import me.munchii.industrialrebornexperimental.config.IndustrialRebornConfig;
import me.munchii.industrialrebornexperimental.init.IREBlockEntities;
import me.munchii.industrialrebornexperimental.init.IREContent;
import me.munchii.industrialrebornexperimental.utils.EntityStorageNBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.blockentity.RedstoneConfiguration;
import reborncore.common.screen.BuiltScreenHandler;
import reborncore.common.screen.BuiltScreenHandlerProvider;
import reborncore.common.screen.builder.ScreenHandlerBuilder;
import reborncore.common.util.RebornInventory;
import techreborn.blockentity.machine.GenericMachineBlockEntity;

import java.util.Optional;

public class PoweredSpawnerBlockEntity extends GenericMachineBlockEntity implements BuiltScreenHandlerProvider {
    // TODO make textures
    public int spawnTime = 0;
    // TODO: make this configurable
    public int totalSpawnTime = 200;

    public final int coreSlot = 0;
    // TODO: make this configurable
    public final int range = 8;

    public String entityType = "";
    public NbtCompound entityTag = null;

    public PoweredSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(IREBlockEntities.POWERED_SPAWNER, pos, state, "PoweredSpawner", IndustrialRebornConfig.poweredSpawnerMaxInput, IndustrialRebornConfig.poweredSpawnerMaxEnergy, IREContent.Machine.POWERED_SPAWNER.block, 1);

        this.inventory = new RebornInventory<>(2, "PoweredSpawnerBlockEntity", 64, this);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, MachineBaseBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);

        if (!(world instanceof ServerWorld serverWorld) || getStored() <= IndustrialRebornConfig.poweredSpawnerEnergyPerSpawn || !isActive(RedstoneConfiguration.POWER_IO)) {
            return;
        }

        if (!this.inventory.getStack(coreSlot).isEmpty() && entityTag == null) {
            ItemStack stack = this.inventory.getStack(coreSlot);

            Optional<NbtCompound> tag = EntityStorageNBTHelper.getEntityDataCompound(stack);
            tag.ifPresent(nbtCompound -> {
                entityTag = nbtCompound;

                Optional<Entity> entity = EntityType.getEntityFromNbt(entityTag, world);
                entity.ifPresent(ent -> entityType = ent.getDisplayName().getString());
            });
        } else if (this.inventory.getStack(coreSlot).isEmpty() && entityTag != null) {
            entityType = "";
            entityTag = null;
        }

        if (getStored() > IndustrialRebornConfig.poweredSpawnerEnergyPerSpawn) {
            if (hasSoul()) {
                if (spawnTime == totalSpawnTime) {
                    spawnEntity(world, pos, entityTag, range);
                    useEnergy(IndustrialRebornConfig.poweredSpawnerEnergyPerSpawn);
                    spawnTime = 0;
                } else {
                    spawnTime++;
                }
            } else if (spawnTime > 0) {
                // reset spawn progress if core is removed
                spawnTime = 0;
            }
        }
    }

    private static void spawnEntity(World world, BlockPos pos, NbtCompound entityTag, int range) {
        double spawnX = pos.getX() + world.random.nextBetween(-range, range);
        double spawnY = pos.getY() + 0.5;
        double spawnZ = pos.getZ() + world.random.nextBetween(-range, range);

        Optional<Entity> entity = EntityType.getEntityFromNbt(entityTag, world);

        entity.ifPresent(ent -> {
            ent.setPos(spawnX, spawnY, spawnZ);
            ent.applyRotation(BlockRotation.random(world.getRandom()));
            world.spawnEntity(ent);
        });
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, final PlayerEntity player) {
        return new ScreenHandlerBuilder("powered_spawner").player(player.getInventory()).inventory().hotbar().addInventory().blockEntity(this)
                .filterSlot(0, 80, 54, PoweredSpawnerBlockEntity::filledVialFilter).energySlot(1, 8, 72).syncEnergyValue()
                .sync(this::getSpawnTime, this::setSpawnTime)
                .sync(this::getTotalSpawnTime, this::setTotalSpawnTime)
                .sync(this::getEntityType, this::setEntityType).addInventory().create(this, syncID);
    }

    public static boolean filledVialFilter(ItemStack stack) {
        Item item = stack.getItem();
        if (item == IREContent.FILLED_SOUL_VIAL) {
            return EntityStorageNBTHelper.hasStoredEntity(stack);
        }

        return false;
    }

    @Override
    public boolean canBeUpgraded() {
        // TODO make upgradable
        return false;
    }

    public int getScaledSpawnTime(final int i) {
        return (int) ((float) spawnTime / (float) totalSpawnTime * i);
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(final int spawnTime) {
        this.spawnTime = spawnTime;
    }

    public int getTotalSpawnTime() {
        return totalSpawnTime;
    }

    public void setTotalSpawnTime(final int totalSpawnTime) {
        this.totalSpawnTime = totalSpawnTime;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public boolean hasSoul() {
        return entityTag != null;
    }
}
