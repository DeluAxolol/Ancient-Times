package com.delu.ancienttimes.common.events;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModBlocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AncientTimes.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ToolActionEvents {

    @SubscribeEvent
    public static void axeStrip(BlockEvent.BlockToolModificationEvent event){
        if (event.getToolAction() == ToolActions.AXE_STRIP){
            BlockState toStrip = event.getContext().getLevel().getBlockState(event.getContext().getClickedPos());
            if (toStrip.is(ModBlocks.MEAL_LOG.get())){
                event.setFinalState(ModBlocks.STRIPPED_MEAL_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, toStrip.getValue(RotatedPillarBlock.AXIS)));
            }
        }
    }
}
