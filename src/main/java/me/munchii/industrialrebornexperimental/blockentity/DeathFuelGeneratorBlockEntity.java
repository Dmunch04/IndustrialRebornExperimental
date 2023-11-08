package me.munchii.industrialrebornexperimental.blockentity;

import me.munchii.industrialrebornexperimental.IndustrialRebornExperimental;
import me.munchii.industrialrebornexperimental.config.IndustrialRebornConfig;
import me.munchii.industrialrebornexperimental.init.IREBlockEntities;
import me.munchii.industrialrebornexperimental.init.IREContent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.screen.BuiltScreenHandler;
import reborncore.common.screen.BuiltScreenHandlerProvider;
import reborncore.common.screen.builder.ScreenHandlerBuilder;
import reborncore.common.util.RebornInventory;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeathFuelGeneratorBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {
    public final RebornInventory<DeathFuelGeneratorBlockEntity> inventory = new RebornInventory<>(2, "DeathFuelGeneratorBlockEntity", 64, this);
    public final int fuelSlot = 0;
    public int burnTime = 0;
    public int totalBurnTime = 0;
    public boolean isBurning;
    public boolean lastTickBurning;
    ItemStack burnItem;

    public DeathFuelGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(IREBlockEntities.DEATH_FUEL_GENERATOR, pos, state);
    }

    public static int getItemBurnTime(@NotNull ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }

        Map<Item, Integer> fuelTimeMap = Stream.of(DeathFuels.values()).collect(Collectors.toMap(data -> data.item, data -> data.burnTime));
        if (fuelTimeMap.containsKey(stack.getItem())) {
            return fuelTimeMap.get(stack.getItem()) / 4;
        }

        return 0;
    }

    private void updateState() {
        assert world != null;

        final BlockState BlockStateContainer = world.getBlockState(pos);
        if (BlockStateContainer.getBlock() instanceof final BlockMachineBase blockMachineBase) {
            boolean active = burnTime > 0 && getFreeSpace() > 0.0f;
            if (BlockStateContainer.get(BlockMachineBase.ACTIVE) != active) {
                blockMachineBase.setActive(active, world, pos);
            }
        }
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, MachineBaseBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        if (world == null || world.isClient) {
            return;
        }

        discharge(1);
        if (getFreeSpace() >= IndustrialRebornConfig.deathGeneratorEnergyPerTick) {
            if (burnTime > 0) {
                burnTime--;
                addEnergy(IndustrialRebornConfig.deathGeneratorEnergyPerTick);
                isBurning = true;
            }
        } else {
            isBurning = false;
            updateState();
        }

        if (burnTime == 0) {
            updateState();
            burnTime = totalBurnTime = DeathFuelGeneratorBlockEntity.getItemBurnTime(inventory.getStack(fuelSlot));
            if (burnTime > 0) {
                updateState();
                burnItem = inventory.getStack(fuelSlot);
                if (inventory.getStack(fuelSlot).getCount() == 1) {
                    if (inventory.getStack(fuelSlot).getItem() == Items.LAVA_BUCKET || inventory.getStack(fuelSlot).getItem() instanceof BucketItem) {
                        inventory.setStack(fuelSlot, new ItemStack(Items.BUCKET));
                    } else {
                        inventory.setStack(fuelSlot, ItemStack.EMPTY);
                    }
                } else {
                    inventory.shrinkSlot(fuelSlot, 1);
                }
            }
        }

        lastTickBurning = isBurning;
    }

    @Override
    public long getBaseMaxPower() {
        return IndustrialRebornConfig.deathGeneratorMaxEnergy;
    }

    @Override
    public boolean canAcceptEnergy(@Nullable Direction side) {
        return false;
    }

    @Override
    public long getBaseMaxOutput() {
        return IndustrialRebornConfig.deathGeneratorMaxOutput;
    }

    @Override
    public long getBaseMaxInput() {
        return 0;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        burnTime = tag.getInt("BurnTime");
        totalBurnTime = tag.getInt("TotalBurnTime");
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("TotalBurnTime", totalBurnTime);
    }

    @Override
    public boolean canBeUpgraded() {
        return false;
    }

    @Override
    public ItemStack getToolDrop(PlayerEntity player) {
        return IREContent.Machine.DEATH_FUEL_GENERATOR.getStack();
    }

    @Override
    public RebornInventory<DeathFuelGeneratorBlockEntity> getInventory() {
        return inventory;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(final int burnTime) {
        this.burnTime = burnTime;
    }

    public int getTotalBurnTime() {
        return totalBurnTime;
    }

    public void setTotalBurnTime(final int totalBurnTime) {
        this.totalBurnTime = totalBurnTime;
    }

    public int getScaledBurnTime(final int i) {
        return (int) ((float) burnTime / (float) totalBurnTime * i);
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, final PlayerEntity player) {
        return new ScreenHandlerBuilder("death_generator").player(player.getInventory()).inventory().hotbar().addInventory()
                .blockEntity(this).filterSlot(0, 80, 54, DeathFuelGeneratorBlockEntity::burnableItemFilter)/*.fuelSlot(0, 80, 54)*/.energySlot(1, 8, 72).syncEnergyValue()
                .sync(this::getBurnTime, this::setBurnTime)
                .sync(this::getTotalBurnTime, this::setTotalBurnTime).addInventory().create(this, syncID);
    }

    public static boolean burnableItemFilter(ItemStack stack) {
        Item item = stack.getItem();
        for (DeathFuels df : DeathFuels.values()) {
            if (item.equals(df.item)) {
                return true;
            }
        }

        return false;
    }

    public enum DeathFuels {
        ROTTEN_FLESH(Items.ROTTEN_FLESH, 300),
        BONE(Items.BONE, 1600),
        BONE_BLOCK(Items.BONE_BLOCK, 16000),
        SPIDER_EYE(Items.SPIDER_EYE, 1600),
        ENDER_PEARL(Items.ENDER_PEARL, 20000),
        CREEPER_HEAD(Items.CREEPER_HEAD, 50000),
        SKELETON_SKULL(Items.SKELETON_SKULL, 50000),
        ZOMBIE_HEAD(Items.ZOMBIE_HEAD, 50000);

        public static final Map<Item, Integer> FUEL_TIME_MAP = Stream.of(values()).collect(Collectors.toMap(data -> data.item, data -> data.burnTime));

        private final Item item;
        private final int burnTime;

        DeathFuels(Item item, int burnTime) {
            this.item = item;
            this.burnTime = burnTime;
        }

        public Item getItem() {
            return item;
        }

        public long getBurnTime() {
            return burnTime;
        }
    }
}
