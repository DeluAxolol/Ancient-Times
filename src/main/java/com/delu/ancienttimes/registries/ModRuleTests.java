package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.rule_tests.RandomTagMatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModRuleTests {


    public static final DeferredRegister<RuleTestType<?>> RULE_TEXT_TYPES = DeferredRegister.create(Registries.RULE_TEST, AncientTimes.MODID);


    public static final RegistryObject<RuleTestType<RandomTagMatch>> RANDOM_TAG_MATCH = RULE_TEXT_TYPES.register("random_tag_match", () -> () -> RandomTagMatch.CODEC);
}
