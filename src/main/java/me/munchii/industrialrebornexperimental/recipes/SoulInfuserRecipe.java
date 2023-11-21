package me.munchii.industrialrebornexperimental.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.ingredient.RebornIngredient;

import java.util.Collections;
import java.util.List;

public class SoulInfuserRecipe extends RebornRecipe {
    private final ShapedRecipe shapedRecipe;
    private final JsonObject shapedRecipeJson;

    public SoulInfuserRecipe(RebornRecipeType<?> type, List<RebornIngredient> ingredients, List<ItemStack> outputs, int power, int time, ShapedRecipe shapedRecipe, JsonObject shapedRecipeJson) {
        super(type, new Identifier("industrialrebornexperimental:soul_infuser"), ingredients, outputs, power, time);

        this.shapedRecipe = shapedRecipe;
        this.shapedRecipeJson = shapedRecipeJson;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return shapedRecipe.getIngredients();
    }

    @Override
    public List<RebornIngredient> getRebornIngredients() {
        return DefaultedList.of();
    }

    @Override
    public List<ItemStack> getOutputs(@Nullable DynamicRegistryManager registryManager) {
        return Collections.singletonList(shapedRecipe.getOutput(registryManager));
    }

    @Override
    public boolean matches(Inventory inv, World worldIn) {
        return shapedRecipe.matches((CraftingInventory) inv, worldIn);
    }

    @Override
    public ItemStack craft(Inventory inventory, @Nullable DynamicRegistryManager dynamicRegistryManager) {
        return shapedRecipe.craft((CraftingInventory) inventory, dynamicRegistryManager);
    }

    @Override
    public boolean fits(int width, int height) {
        return shapedRecipe.fits(width, height);
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager dynamicRegistryManager) {
        return shapedRecipe.getOutput(dynamicRegistryManager);
    }

    public ShapedRecipe getShapedRecipe() {
        return shapedRecipe;
    }

    public JsonObject getShapedRecipeJson() {
        return shapedRecipeJson;
    }
}
