package me.munchii.industrialrebornexperimental.items;

import me.munchii.industrialrebornexperimental.init.IREContent;
import me.munchii.industrialrebornexperimental.utils.EntityCaptureUtils;
import me.munchii.industrialrebornexperimental.utils.EntityStorageNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Consumer;

public class SoulVialItem extends Item {
    public SoulVialItem(boolean filled) {
        super(new Item.Settings().maxCount(filled ? 1 : 16));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // check if user is client-side, and if so return ActionResult.FAIL
        if (context.getWorld().isClient) {
            return ActionResult.FAIL;
        }

        PlayerEntity player = context.getPlayer();

        if (player == null) {
            return ActionResult.FAIL;
        }

        return releaseEntity(context.getWorld(), context.getStack(), context.getSide(), context.getBlockPos(), emptyVial -> player.setStackInHand(context.getHand(), emptyVial));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        // check if user is client-side, and if so return ActionResult.FAIL
        if (user.getWorld().isClient) {
            return ActionResult.FAIL;
        }

        Optional<ItemStack> itemStack = catchEntity(stack, entity);
        if (itemStack.isPresent()) {
            ItemStack filledVial = itemStack.get();
            ItemStack handStack = user.getStackInHand(hand);
            if (handStack.isEmpty()) {
                handStack.setCount(1);
                user.setStackInHand(hand, filledVial);
            } else {
                if (!user.giveItemStack(filledVial)) {
                    user.dropItem(filledVial, false);
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    private static Optional<ItemStack> catchEntity(ItemStack soulVial, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            // tell/show player that it failed
            return Optional.empty();
        }

        EntityCaptureUtils.CapturableStatus capturableStatus = EntityCaptureUtils.getCapturableStatus((EntityType<? extends LivingEntity>) entity.getType(), entity);
        if (capturableStatus != EntityCaptureUtils.CapturableStatus.CAPTURABLE) {
            // tell/show player that it failed
            return Optional.empty();
        }

        if (!entity.isAlive()) {
            // tell/show player that it failed
            return Optional.empty();
        }

        if (entity instanceof MobEntity mob && mob.isLeashed()) {
            mob.detachLeash(true, true);
        }

        soulVial.decrement(1);
        ItemStack filledVial = IREContent.FILLED_SOUL_VIAL.getDefaultStack();
        EntityStorageNBTHelper.saveEntityData(filledVial, entity);

        entity.discard();
        return Optional.of(filledVial);
    }

    private static ActionResult releaseEntity(World world, ItemStack filledVial, Direction face, BlockPos pos, Consumer<ItemStack> emptyVialSetter) {
        if (EntityStorageNBTHelper.hasStoredEntity(filledVial)) {
            Optional<NbtCompound> entityTag = EntityStorageNBTHelper.getEntityDataCompound(filledVial);
            if (!entityTag.isPresent()) {
                return ActionResult.FAIL;
            }

            double spawnX = pos.getX();// + face.getOffsetX() + 0.5;
            double spawnY = pos.getY();// + face.getOffsetY();
            double spawnZ = pos.getZ();// + face.getOffsetZ() + 0.5;

            Optional<Entity> entity = EntityType.getEntityFromNbt(entityTag.get(), world);

            entity.ifPresent(ent -> {
                ent.setPos(spawnX, spawnY, spawnZ);
                ent.applyRotation(BlockRotation.random(world.getRandom()));
                world.spawnEntity(ent);
            });

            emptyVialSetter.accept(IREContent.EMPTY_SOUL_VIAL.getDefaultStack());
        }

        return ActionResult.SUCCESS;
    }
}
