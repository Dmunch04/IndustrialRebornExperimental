package me.munchii.industrialrebornexperimental.client.gui;

import me.munchii.industrialrebornexperimental.blockentity.SoulFuserBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiBuilder;
import reborncore.common.screen.BuiltScreenHandler;

public class GuiSoulFuser extends GuiBase<BuiltScreenHandler> {
    final SoulFuserBlockEntity blockEntity;

    public GuiSoulFuser(int syncID, final PlayerEntity player, final SoulFuserBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawBackground(DrawContext drawContext, final float f, final int mouseX, final int mouseY) {
        super.drawBackground(drawContext, f, mouseX, mouseY);
        final Layer layer = Layer.BACKGROUND;

        drawSlot(drawContext, 8, 72, layer);

        drawSlot(drawContext, 55, 55, layer);
        drawSlot(drawContext, 55, 35, layer);
        drawOutputSlot(drawContext, 101, 45, layer);
        drawOutputSlot(drawContext, 131, 45, layer);
    }

    @Override
    protected void drawForeground(DrawContext drawContext, final int mouseX, final int mouseY) {
        super.drawForeground(drawContext, mouseX, mouseY);
        final Layer layer = Layer.FOREGROUND;

        builder.drawProgressBar(drawContext, this, blockEntity.getProgressScaled(100), 100, 76, 48, mouseX, mouseY, GuiBuilder.ProgressDirection.RIGHT, layer);
        builder.drawMultiEnergyBar(drawContext, this, 9, 19, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxStoredPower(), mouseX, mouseY, 0, layer);
    }
}
