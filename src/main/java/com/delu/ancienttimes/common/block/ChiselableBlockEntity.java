package com.delu.ancienttimes.common.block;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChiselableBlockEntity extends BrushableBlockEntity {
    public ChiselableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }
    @Override
    public boolean brush(long pStartTick, Player pPlayer, Direction pHitDirection) {
        // Check if the player is holding the custom chisel
        ItemStack heldItem = pPlayer.getMainHandItem();
        if (!heldItem.is(ModItems.DIAMOND_CHISEL.get())) { // Assuming ModItems.CHISEL is your custom chisel
            AncientTimes.LOGGER.info("Player is not holding the chisel!");
            return false; // Do nothing if the player isn't holding the chisel
        }

        AncientTimes.LOGGER.info("Chisel used: " + heldItem);
        AncientTimes.LOGGER.info("Brushing with chisel...");

        // Proceed with the parent class logic if chisel is held
        return super.brush(pStartTick, pPlayer, pHitDirection);
    }
}