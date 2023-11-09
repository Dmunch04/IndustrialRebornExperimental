package me.munchii.industrialrebornexperimental.init;

import net.minecraft.util.Identifier;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.RecipeManager;

public class IRERecipes {
    public static final RebornRecipeType<RebornRecipe> SOUL_FUSER = RecipeManager.newRecipeType(new Identifier("industrialrebornexperimental:soul_fuser"));

    public static void init() {
        SOUL_FUSER.hashCode();
    }
}
