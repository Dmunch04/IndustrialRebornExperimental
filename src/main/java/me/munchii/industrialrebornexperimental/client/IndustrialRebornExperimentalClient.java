package me.munchii.industrialrebornexperimental.client;

import net.fabricmc.api.ClientModInitializer;

public class IndustrialRebornExperimentalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientGuiType.validate();
    }
}
