package me.munchii.industrialrebornexperimental.client.gui;

import me.munchii.industrialrebornexperimental.blockentity.DeathFuelGeneratorBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import reborncore.client.gui.GuiBase;
import reborncore.common.screen.BuiltScreenHandler;

public class GuiDeathGenerator extends GuiBase<BuiltScreenHandler> {
    final DeathFuelGeneratorBlockEntity blockEntity;

    public GuiDeathGenerator(int syncID, final PlayerEntity player, final DeathFuelGeneratorBlockEntity blockEntity) {
        super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawBackground(DrawContext drawContext, final float f, final int mouseX, final int mouseY) {
        super.drawBackground(drawContext, f, mouseX, mouseY);
        final Layer layer = Layer.BACKGROUND;

        drawSlot(drawContext, 8, 72, layer);
        drawSlot(drawContext, 80, 54, layer);
    }

    @Override
    protected void drawForeground(DrawContext drawContext, final int mouseX, final int mouseY) {
        super.drawForeground(drawContext, mouseX, mouseY);
        final Layer layer = Layer.FOREGROUND;

        builder.drawBurnBar(drawContext, this, blockEntity.getScaledBurnTime(100), 100, 81, 38, mouseX, mouseY, layer);
        builder.drawMultiEnergyBar(drawContext, this, 9, 19, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxStoredPower(), mouseX, mouseY, 0, layer);
    }
}
