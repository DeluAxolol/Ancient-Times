package com.delu.ancienttimes.common.entity;

import com.delu.ancienttimes.common.entity.goal.BlockFindGoal;
import com.delu.ancienttimes.common.tags.ModTags;
import com.delu.ancienttimes.common.util.NbtUtils;
import com.delu.ancienttimes.registries.ModEntities;
import com.delu.ancienttimes.registries.ModLootTables;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Trioclantus extends Animal implements GeoEntity {

    public static final EntityDataAccessor<Boolean> DIGGING = SynchedEntityData.defineId(Trioclantus.class, EntityDataSerializers.BOOLEAN);

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40d)
                .add(Attributes.ATTACK_DAMAGE, 8d)//im not sure but i think this entity needs that stat
                .add(Attributes.MOVEMENT_SPEED, 0.25d);
    }

    protected AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected int diggingCooldown, diggingAnimationCooldown;
    public Trioclantus(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, -1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4d));
        //TODO add actual animation cooldown when animations are there, so the particles spawn when the animation is over
        this.goalSelector.addGoal(2, new DiggingGoal(this, 40, 1000, 300));

        this.goalSelector.addGoal(3, new BlockFindGoal<>(this, 20, this::canDigOnBlock, Trioclantus::canDig));

        //yeah anonymous classes arent the best, but im to lazy to make every of these classes with a custom predicate
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6f) {
            @Override
            public boolean canContinueToUse() {
                return !isDigging() && super.canContinueToUse();
            }

            @Override
            public boolean canUse() {
                return !isDigging() && super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            @Override
            public boolean canContinueToUse() {
                return !isDigging() && super.canContinueToUse();
            }

            @Override
            public boolean canUse() {
                return !isDigging() && super.canUse();
            }
        });
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1d) {
            @Override
            public boolean canContinueToUse() {
                return !isDigging() && super.canContinueToUse();
            }

            @Override
            public boolean canUse() {
                return !isDigging() && super.canUse();
            }
        });
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DIGGING, false);
    }

    public void onPathfindingStart() {
        super.onPathfindingStart();
        if (this.isOnFire() || this.isInWater()) {
            this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        NbtUtils.setIfExists(pCompound, "digging", CompoundTag::getBoolean, this.entityData, DIGGING);
        NbtUtils.setIfExists(pCompound, "diggingCooldown", CompoundTag::getInt, i -> diggingCooldown = i);
        NbtUtils.setIfExists(pCompound, "diggingAnimationCooldown", CompoundTag::getInt, i -> diggingAnimationCooldown = i);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("digging", isDigging());
        pCompound.putInt("diggingCooldown", this.diggingCooldown);
        pCompound.putInt("diggingAnimationCooldown", this.diggingAnimationCooldown);
    }

    public boolean canDigOnBlock(BlockState state) {
        return state.is(ModTags.Blocks.TRIOCLANTUS_DIGGABLES);
    }

    public void onPathfindingDone() {
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    protected boolean canDig(){
        return this.diggingCooldown <= 0;
    }

    public boolean canSniff() {
        return !this.isInWater() && !this.isInLove() && this.onGround() && !this.isPassenger();
    }
    protected PlayState predicate(AnimationState<Trioclantus> event){


        return PlayState.CONTINUE;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ItemTags.SAPLINGS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.TRIOCLANTUS.get().create(pLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 10, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (this.diggingCooldown > 0)
                this.diggingCooldown--;
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 10) {
            BlockParticleOption option = new BlockParticleOption(ParticleTypes.BLOCK, level().getBlockState(getOnPos()));
            for (int i = 0; i < 10; i++) {
                level().addParticle(option, getX(), getY(), getZ(), 0, 0.1f, 0);
            }
        } else
            super.handleEntityEvent(pId);
    }

    protected void setDigging(boolean digging) {
        this.entityData.set(DIGGING, digging);
    }

    public boolean isDigging() {
        return entityData.get(DIGGING);
    }

    protected boolean shouldPanic() {
        return getLastHurtByMob() != null || isFreezing() || isOnFire();
    }



    public static class DiggingGoal extends Goal {
        protected final Trioclantus animal;
        protected final int animationCooldown, avgCooldown, range;

        /**
         * @param animal            the animal this goa is applied to
         * @param animationCooldown the time it takes to fully play the animation
         * @param avgCooldown       the average cooldown until the entity can dig again
         * @param range             the range around the avg cooldown
         *                          note that the cooldown is binomial distributed
         */
        public DiggingGoal(Trioclantus animal, int animationCooldown, int avgCooldown, int range) {
            this.animal = animal;
            this.animationCooldown = animationCooldown;
            this.avgCooldown = avgCooldown;
            this.range = range;
        }


        @Override
        public boolean canUse() {
            return animal.canDigOnBlock(animal.getBlockStateOn()) && !animal.shouldPanic() && animal.getLastHurtByMob() == null && !animal.isBaby() && animal.diggingCooldown <= 0;
        }

        @Override
        public void start() {
            animal.setDigging(true);
            animal.diggingAnimationCooldown = animationCooldown;
            animal.goalSelector.disableControlFlag(Flag.MOVE);
            animal.goalSelector.disableControlFlag(Flag.LOOK);
        }

        @Override
        public void tick() {
            if (animal.diggingAnimationCooldown > 0) {
                animal.diggingAnimationCooldown--;
                animal.level().broadcastEntityEvent(animal, (byte) 10);
            }
            if (animal.diggingAnimationCooldown <= 0) {
                animal.diggingCooldown = avgCooldown + animal.random.nextInt(range) - animal.random.nextInt(range);
                spawnDrops();
            }
        }

        protected void spawnDrops() {
            LootParams params = new LootParams.Builder((ServerLevel) animal.level()).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(animal.getOnPos())).withParameter(LootContextParams.THIS_ENTITY, animal).create(LootContextParamSets.ARCHAEOLOGY);
            LootTable loottable = animal.level().getServer().getLootData().getLootTable(ModLootTables.TRIOCLANTUS_DIGGING_DROPS);
            List<ItemStack> list = loottable.getRandomItems(params);
            list.forEach(animal::spawnAtLocation);
        }

        @Override
        public void stop() {
            animal.goalSelector.enableControlFlag(Flag.MOVE);
            animal.goalSelector.enableControlFlag(Flag.LOOK);
            animal.setDigging(false);
            animal.diggingAnimationCooldown = 0;
        }
    }
}
