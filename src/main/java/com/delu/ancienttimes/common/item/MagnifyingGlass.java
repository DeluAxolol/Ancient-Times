package com.delu.ancienttimes.common.item;

import com.delu.ancienttimes.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickPriority;

public class MagnifyingGlass extends Item {

    public MagnifyingGlass(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        ItemStack stack = player.getItemInHand(hand);

        // Check cooldown
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.pass(stack);
        }

        // Glow blocks in a 5-block radius
        BlockPos playerPos = player.blockPosition();
        AABB area = new AABB(playerPos).inflate(5);

        ServerLevel serverLevel = (ServerLevel) level;

        // Schedule particle effects for 200 ticks
        for (int tick = 0; tick < 200; tick++) {
            final int delay = tick; // Capture delay for scheduling
            serverLevel.scheduleTick(playerPos, ModBlocks.SUS_ANDESITE.get(), delay);

            // Logic to execute on each scheduled tick
            serverLevel.getBlockTicks().schedule(
                    new ScheduledTick<>(
                            ModBlocks.SUS_ANDESITE.get(), // Dummy block type
                            playerPos,
                            level.getGameTime() + delay, // Trigger time
                            TickPriority.NORMAL, // Priority
                            tick // Sub-tick order (if necessary for precise execution)
                    )
            );

            serverLevel.getServer().execute(() -> {
                for (BlockPos pos : BlockPos.betweenClosed(
                        (int) area.minX, (int) area.minY, (int) area.minZ,
                        (int) area.maxX, (int) area.maxY, (int) area.maxZ)) {
                    BlockState blockState = level.getBlockState(pos);

                    // Check for specific block types
                    if (blockState.is(ModBlocks.SUS_ANDESITE.get())
                            || blockState.is(ModBlocks.SUS_CLAY.get())
                            || blockState.is(ModBlocks.SUS_DIRT.get())
                            || blockState.is(ModBlocks.SUS_SNOW.get())
                            || blockState.is(Blocks.SUSPICIOUS_SAND)
                            || blockState.is(Blocks.SUSPICIOUS_GRAVEL)
                            || blockState.is(ModBlocks.SUS_MUD.get())
                            || blockState.is(ModBlocks.SUS_RED_SAND.get())) {
                        serverLevel.sendParticles(
                                net.minecraft.core.particles.ParticleTypes.GLOW,
                                pos.getX() + 0.5,
                                pos.getY() + 0.5,
                                pos.getZ() + 0.5,
                                1, // Particle count
                                0.25, 0.25, 0.25, // Offset
                                0.0001 // Speed
                        );
                    }
                }
            });
        }



        // remove 1 durability from the magnifying glass
        stack.hurtAndBreak(1, player, (playerEntity) -> {
            playerEntity.broadcastBreakEvent(hand);
        });
        // Apply cooldown (2 seconds = 40 ticks)
        player.getCooldowns().addCooldown(this, 100);

        return InteractionResultHolder.success(stack);
    }
}