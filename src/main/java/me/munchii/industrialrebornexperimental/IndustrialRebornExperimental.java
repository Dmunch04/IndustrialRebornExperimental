package me.munchii.industrialrebornexperimental;

import me.munchii.industrialrebornexperimental.config.IndustrialRebornConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reborncore.common.config.Configuration;

public class IndustrialRebornExperimental implements ModInitializer {
    public static final String MOD_ID = "industrialrebornexperimental";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        new Configuration(IndustrialRebornConfig.class, MOD_ID);

        RegistryManager.register();
    }
}
