package com.delu.ancienttimes.common.item;

import com.delu.ancienttimes.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DiamondChisel extends BrushItem {
    public DiamondChisel(Properties pProperties) {
        super(pProperties);
    }
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);

        // Define the block you want to allow the interaction with
        Block targetBlock = ModBlocks.SUS_ANDESITE.get(); // Replace with your specific block

        // Check if the block being interacted with is the target block
        if (blockState.getBlock() == targetBlock) {
            Player player = context.getPlayer();
            if (player != null ) {
                player.startUsingItem(context.getHand());
            }

            return InteractionResult.CONSUME;
        }

        // If the block is not the target block, do nothing
        return InteractionResult.PASS;
    }

}
