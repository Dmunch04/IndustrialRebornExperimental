package me.munchii.industrialrebornexperimental.blockentity;

import me.munchii.industrialrebornexperimental.config.IndustrialRebornConfig;
import me.munchii.industrialrebornexperimental.init.IREBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.api.IToolDrop;
import reborncore.api.blockentity.InventoryProvider;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.screen.BuiltScreenHandler;
import reborncore.common.screen.BuiltScreenHandlerProvider;
import reborncore.common.util.RebornInventory;

import java.util.List;

public class SoulInfuserBlockEntity extends PowerAcceptorBlockEntity implements IToolDrop, InventoryProvider, BuiltScreenHandlerProvider {

    // https://github.com/TechReborn/TechReborn/blob/1.20/src/main/java/techreborn/blockentity/machine/tier1/RollingMachineBlockEntity.java
    // current problem to solve: pass NBT data from soul vial to the primary output slot's result item/block

    public final RebornInventory<SoulInfuserBlockEntity> inventory = new RebornInventory<>(5, "SoulInfuserBlockEntity", 64, this);

    public int[] inputSlots = new int[]{0, 1};
    private final int INPUT_SOUL = 0;
    private final int INPUT_OTHER = 1;
    public int[] outputSlots = new int[]{2, 3};
    private final int OUTPUT_PRIMARY = 2;
    private final int OUTPUT_OTHER = 3;

    public SoulInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(IREBlockEntities.SOUL_INFUSER, pos, state);
    }

    @Override
    public long getBaseMaxPower() {
        return IndustrialRebornConfig.soulFuserMaxEnergy;
    }

    @Override
    public long getBaseMaxOutput() {
        return 0;
    }

    @Override
    public long getBaseMaxInput() {
        return IndustrialRebornConfig.soulFuserMaxInput;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, MachineBaseBlockEntity blockEntity2) {
        super.tick(world, pos, state, blockEntity2);

        if (world == null || world.isClient) {
            return;
        }
    }

    @Override
    public ItemStack getToolDrop(PlayerEntity playerEntity) {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int i, PlayerEntity playerEntity) {
        return null;
    }
}
