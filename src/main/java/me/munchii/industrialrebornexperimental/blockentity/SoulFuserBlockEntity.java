package me.munchii.industrialrebornexperimental.blockentity;

import me.munchii.industrialrebornexperimental.config.IndustrialRebornConfig;
import me.munchii.industrialrebornexperimental.init.IREBlockEntities;
import me.munchii.industrialrebornexperimental.init.IREContent;
import me.munchii.industrialrebornexperimental.init.IRERecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.screen.BuiltScreenHandler;
import reborncore.common.screen.BuiltScreenHandlerProvider;
import reborncore.common.screen.builder.ScreenHandlerBuilder;
import reborncore.common.util.RebornInventory;
import techreborn.blockentity.machine.GenericMachineBlockEntity;

public class SoulFuserBlockEntity extends GenericMachineBlockEntity implements BuiltScreenHandlerProvider {
    // TODO make texture
    public SoulFuserBlockEntity(BlockPos pos, BlockState state) {
        super(IREBlockEntities.SOUL_FUSER, pos, state, "SoulFuser", IndustrialRebornConfig.soulFuserMaxInput, IndustrialRebornConfig.soulFuserMaxEnergy, IREContent.Machine.SOUL_FUSER.block, 4);

        final int[] inputs = new int[]{0, 1};
        final int[] outputs = new int[]{2, 3};
        this.inventory = new RebornInventory<>(5, "SoulFuserBlockEntity", 64, this);
        this.crafter = new RecipeCrafter(IRERecipes.SOUL_FUSER, this, 3, 2, this.inventory, inputs, outputs);
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, final PlayerEntity player) {
        return new ScreenHandlerBuilder("soul_fuser").player(player.getInventory()).inventory().hotbar().addInventory().blockEntity(this)
                .slot(0, 55, 55).slot(1, 55, 35).outputSlot(2, 101, 45).outputSlot(3, 131, 45).energySlot(4, 8, 72).syncEnergyValue().syncCrafterValue()
                .addInventory().create(this, syncID);
    }
}
