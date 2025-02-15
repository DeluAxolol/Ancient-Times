package com.delu.ancienttimes.common.item;

import com.delu.ancienttimes.registries.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class RavenheadsFruitEntity extends ThrownEntity {

    public RavenheadsFruitEntity(EntityType<? extends RavenheadsFruitEntity> entityType, World world) {
        super(entityType, world);
    }

    public RavenheadsFruitEntity(World world, PlayerEntity thrower) {
        super(ModEntities.RAVENHEADS_FRUIT_ENTITY, thrower, world); // Replace with your custom EntityType if necessary
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.getWorld().isClient) {
            // Create a small explosion on impact
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 0.18F, World.ExplosionSourceType.NONE);

            // Add particles for visual effect
            this.getWorld().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);

            // Play a sound on impact
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.4F, 1.0F);

            this.discard(); // Remove the entity
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!this.getWorld().isClient) {
            // Optional: Deal damage to entities hit
            entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 3.0F);
        }
    }

    @Override
    protected void initDataTracker() {
    }


}