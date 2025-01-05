package com.delu.ancienttimes.common.item;

import com.delu.ancienttimes.registries.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class RavenheadsFruitEntity extends ThrowableProjectile {

    public RavenheadsFruitEntity(EntityType<? extends RavenheadsFruitEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RavenheadsFruitEntity(Level level, Player thrower) {
        super(ModEntities.RAVENHEADS_FRUIT_ENTITY.get(), thrower, level); // Replace with your custom EntityType if necessary
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide) {
            // Create a small explosion on impact
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 0.18F, Level.ExplosionInteraction.NONE);

            // Add particles for visual effect
            this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);

            // Play a sound on impact
            this.playSound(SoundEvents.GENERIC_EXPLODE, 0.4F, 1.0F);

            this.discard(); // Remove the entity
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        if (!this.level().isClientSide) {
            // Optional: Deal damage to entities hit
            entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 3.0F);
        }
    }

    @Override
    protected void defineSynchedData() {
    }


}