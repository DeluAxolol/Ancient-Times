package com.delu.ancienttimes.common.rule_tests;

import com.delu.ancienttimes.registries.ModRuleTests;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.registries.ForgeRegistries;

public class RandomTagMatch extends RuleTest {
    public static final Codec<RandomTagMatch> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TagKey.codec(ForgeRegistries.Keys.BLOCKS).fieldOf("block_tag").forGetter(r -> r.blockTag),
            Codec.FLOAT.fieldOf("chance").forGetter(r -> r.chance)
    ).apply(instance, RandomTagMatch::new));

    protected final TagKey<Block> blockTag;
    protected final float chance;

    public RandomTagMatch(TagKey<Block> blockTag, float chance) {
        this.blockTag = blockTag;
        this.chance = chance;
    }

    @Override
    public boolean test(BlockState pState, RandomSource pRandom) {
        return pState.is(blockTag) && pRandom.nextFloat() < chance;
    }

    @Override
    protected RuleTestType<?> getType() {
        return ModRuleTests.RANDOM_TAG_MATCH.get();
    }
}
