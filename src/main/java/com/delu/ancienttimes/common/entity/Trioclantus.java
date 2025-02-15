package com.delu.ancienttimes.common.entity;

import com.delu.ancienttimes.common.entity.goal.BlockFindGoal;
import com.delu.ancienttimes.common.tags.ModTags;
import com.delu.ancienttimes.common.util.NbtUtils;
import com.delu.ancienttimes.registries.ModEntities;
import com.delu.ancienttimes.registries.ModLootTables;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Trioclantus extends AnimalEntity implements GeoEntity {

    public static final TrackedData<Boolean> DIGGING = DataTracker.registerData(Trioclantus.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static DefaultAttributeContainer.Builder createAttributes(){
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40d)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8d)//im not sure but i think this entity needs that stat
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25d);
    }

    protected AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected int diggingCooldown, diggingAnimationCooldown;
    public Trioclantus(EntityType<? extends AnimalEntity> pEntityType, World world) {
        super(pEntityType, world);

        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_CAUTIOUS, -1.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4d));
        //TODO add actual animation cooldown when animations are there, so the particles spawn when the animation is over
        this.goalSelector.add(2, new DiggingGoal(this, 40, 1000, 300));

        this.goalSelector.add(3, new BlockFindGoal<>(this, 20, this::canDigOnBlock, Trioclantus::canDig));

        //yeah anonymous classes arent the best, but im to lazy to make every of these classes with a custom predicate
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6f) {
            @Override
            public boolean shouldContinue() {
                return !isDigging() && super.shouldContinue();
            }

            @Override
            public boolean canStart() {
                return !isDigging() && super.canStart();
            }
        });
        this.goalSelector.add(5, new LookAroundGoal(this) {
            @Override
            public boolean shouldContinue() {
                return !isDigging() && super.shouldContinue();
            }

            @Override
            public boolean canStart() {
                return !isDigging() && super.canStart();
            }
        });
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1d) {
            @Override
            public boolean shouldContinue() {
                return !isDigging() && super.shouldContinue();
            }

            @Override
            public boolean canStart() {
                return !isDigging() && super.canStart();
            }
        });
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DIGGING, false);
    }

    public void onStartPathfinding() {
        super.onStartPathfinding();
        if (this.isOnFire() || this.isTouchingWater()) {
            this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        }

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);
        NbtUtils.setIfExists(pCompound, "digging", NbtCompound::getBoolean, this.dataTracker, DIGGING);
        NbtUtils.setIfExists(pCompound, "diggingCooldown", NbtCompound::getInt, i -> diggingCooldown = i);
        NbtUtils.setIfExists(pCompound, "diggingAnimationCooldown", NbtCompound::getInt, i -> diggingAnimationCooldown = i);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putBoolean("digging", isDigging());
        pCompound.putInt("diggingCooldown", this.diggingCooldown);
        pCompound.putInt("diggingAnimationCooldown", this.diggingAnimationCooldown);
    }

    public boolean canDigOnBlock(BlockState state) {
        return state.isOf(ModTags.Blocks.TRIOCLANTUS_DIGGABLES);
    }

    public void onPathfindingDone() {
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
    }

    protected boolean canDig(){
        return this.diggingCooldown <= 0;
    }

    public boolean canSniff() {
        return !this.isTouchingWater() && !this.isInLove() && this.isOnGround() && !this.hasVehicle();
    }
    protected PlayState predicate(AnimationState<Trioclantus> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public boolean isBreedingItem(ItemStack pStack) {
        return pStack.isOf(ItemTags.SAPLINGS);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity pOtherParent) {
        return ModEntities.TRIOCLANTUS.create(world);
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
        if (!getWorld().isClient) {
            if (this.diggingCooldown > 0)
                this.diggingCooldown--;
        }
    }

    @Override
    public void handleStatus(byte pId) {
        if (pId == 10) {
            BlockStateParticleEffect option = new BlockStateParticleEffect(ParticleTypes.BLOCK, getWorld().getBlockState(getSteppingPos()));
            for (int i = 0; i < 10; i++) {
                getWorld().addParticle(option, getX(), getY(), getZ(), 0, 0.1f, 0);
            }
        } else
            super.handleStatus(pId);
    }

    protected void setDigging(boolean digging) {
        this.dataTracker.set(DIGGING, digging);
    }

    public boolean isDigging() {
        return dataTracker.get(DIGGING);
    }

    protected boolean shouldPanic() {
        return getAttacker() != null || shouldEscapePowderSnow() || isOnFire();
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
        public boolean canStart() {
            return animal.canDigOnBlock(animal.getSteppingBlockState()) && !animal.shouldPanic() && animal.getAttacker() == null && !animal.isBaby() && animal.diggingCooldown <= 0;
        }

        @Override
        public void start() {
            animal.setDigging(true);
            animal.diggingAnimationCooldown = animationCooldown;
            animal.goalSelector.disableControl(Control.MOVE);
            animal.goalSelector.disableControl(Control.LOOK);
        }

        @Override
        public void tick() {
            if (animal.diggingAnimationCooldown > 0) {
                animal.diggingAnimationCooldown--;
                animal.getWorld().sendEntityStatus(animal, (byte) 10);
            }
            if (animal.diggingAnimationCooldown <= 0) {
                animal.diggingCooldown = avgCooldown + animal.random.nextInt(range) - animal.random.nextInt(range);
                spawnDrops();
            }
        }

        protected void spawnDrops() {
            LootContextParameterSet params = new LootContextParameterSet.Builder((ServerWorld) animal.getWorld())
                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(animal.getSteppingPos()))
                    .add(LootContextParameters.THIS_ENTITY, animal)
                    .add(LootContextTypes.ARCHAEOLOGY);

            LootTable loottable = animal.getWorld().getServer().getLootManager().getLootTable(ModLootTables.TRIOCLANTUS_DIGGING_DROPS);
            List<ItemStack> list = loottable.generateLoot(params);
            list.forEach(animal::dropStack);
        }

        @Override
        public void stop() {
            animal.goalSelector.enableControl(Control.MOVE);
            animal.goalSelector.enableControl(Control.LOOK);
            animal.setDigging(false);
            animal.diggingAnimationCooldown = 0;
        }
    }
}
