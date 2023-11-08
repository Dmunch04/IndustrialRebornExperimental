package me.munchii.industrialrebornexperimental.utils;

import me.munchii.industrialrebornexperimental.IndustrialRebornExperimental;
import net.minecraft.util.Identifier;

public class Resources {
    public static Identifier id(String path) {
        return new Identifier(IndustrialRebornExperimental.MOD_ID, path);
    }
}
