package com.delu.ancienttimes.client.renderer;

import com.delu.ancienttimes.client.model.ATAnimalModel;
import com.delu.ancienttimes.server.entity.ATAnimal;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reaper.skulllib.client.render.entity.NameTagSkins;
import org.reaper.skulllib.server.anim.IAnimateable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Slf4j
@OnlyIn(Dist.CLIENT)
public abstract class ATAnimalRenderer<T extends ATAnimal<T>> extends MobRenderer<T, ATAnimalModel<T>> {
    public static final boolean DEBUG = false;
    final List<ModelEntry<T>> models = new ArrayList<>();
    boolean useNameTagSkins = true;
    final NameTagSkins skins = NameTagSkins.INSTANCE;
    EntityRendererProvider.Context context;

    public ATAnimalRenderer(EntityRendererProvider.@NotNull Context ctx) {
        super(ctx, null, 0.0F);
        this.context = ctx;
        Builder<T> builder = new Builder<>(ctx, this.models);
        this.initModels(builder);
        if (this.models.isEmpty()) {
            throw new IllegalStateException("Renderer has no registered models!");
        }
        if (DEBUG) {
            log.debug("Init AnimalRenderer for {}", this.getClass().getSimpleName());
            for (ModelEntry<T> entry : this.models) {
                log.debug("Model: {}, priority: {}", entry.supplier.get().getClass().getSimpleName(), entry.getPriority());
            }
        }
        this.initSkins();
        ModelEntry<T> base = this.models.get(0);
        this.model = base.model();
        this.shadowRadius = this.model.shadowRadius();
    }

    @Override
    public void render(@NotNull T mob, float yaw, float partialTick, @NotNull PoseStack matrix, @NotNull MultiBufferSource buffer, int light) {
        this.model = this.resolveModel(mob);
        float scale = this.getGrowthScale(mob);
        this.shadowRadius = this.model.shadowRadius() * scale;
        matrix.pushPose();
        if (mob.isBaby() && this.isAdvancedSizeScaling()) {
            matrix.scale(scale, scale, scale);
        }
        super.render(mob, yaw, partialTick, matrix, buffer, light);
        matrix.popPose();
    }

    public float getGrowthScale(@NotNull T mob) {
        if (mob.isBaby() && this.isAdvancedSizeScaling()) {
            float ageProgress = 1.0F - Math.abs((float)mob.getAge()) / 24000.0F;
            ageProgress = Mth.clamp(ageProgress, 0.0F, 1.0F);
            return this.getBabyMinScale() + ageProgress * (this.getBabyMaxScale() - this.getBabyMinScale());
        }
        return 1.0F;
    }

    protected abstract void initModels(@NotNull Builder<T> builder);

    public void initSkins() {

    }

    public boolean isAdvancedSizeScaling() {
        return true;
    }

    public float getBabyMinScale() {
        return 1.0F;
    }

    public float getBabyMaxScale() {
        return 2.5F;
    }

    protected ATAnimalModel<T> resolveModel(@NotNull T mob) {
        return this.models.stream().filter(entry -> entry.test(mob)).findFirst().map(ModelEntry::model).orElseGet(() -> this.models.get(0).model());
    }

    public boolean isAnimPlaying(@NotNull Mob mob, String name) {
        return mob instanceof IAnimateable<?> animable && animable.getAnimator().isPlaying(name);
    }

    public @Nullable List<ModelPart> getHeadPartChain() {
        return List.of();
    }

    public @NotNull Vec3 getOffsetForFearRender() {
        return new Vec3(0.0F, 0.0F, 0.0F);
    }

    @Override
    public @Nullable ResourceLocation getTextureLocation(@NotNull T mob) {
        ResourceLocation tex = this.useNameTagSkins ? this.skins.getTexture(mob) : null;
        if (tex != null) {
            return tex;
        }
        ATAnimalModel<T> model = this.resolveModel(mob);
        if (model == null) {
            throw new IllegalStateException("No model available for entity " + mob);
        }
        return model.getTexture(mob);
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
    protected static class ModelEntry<T extends ATAnimal<T>> {
        @Getter
        int priority;
        Supplier<ATAnimalModel<T>> supplier;
        List<Predicate<T>> conditions;
        @NonFinal
        ATAnimalModel<T> cached;

        public @NotNull ATAnimalModel<T> model() {
            if (this.cached == null) {
                this.cached = this.supplier.get();
            }
            return this.cached;
        }

        public boolean test(@NotNull T mob) {
            return this.conditions.stream().allMatch(pred -> pred.test(mob));
        }
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @OnlyIn(Dist.CLIENT)
    protected static class ModelCondition<T extends ATAnimal<T>> {
        List<Predicate<T>> predicates = new ArrayList<>();

        private ModelCondition() {
        }

        @Contract(" -> new")
        public static <T extends ATAnimal<T>> @NotNull ModelCondition<T> create() {
            return new ModelCondition<>();
        }

        public @NotNull ModelCondition<T> adult() {
            this.predicates.add(mob -> !mob.isBaby());
            return this;
        }

        public @NotNull ModelCondition<T> baby() {
            this.predicates.add(ATAnimal::isBaby);
            return this;
        }

        /*
        public @NotNull ModelCondition<T> gender(@NotNull Gender gender) {
            this.predicates.add(mob -> ATAnimal.GENDER.get(mob) == gender);
            return this;
        }

         */

        public @NotNull ModelCondition<T> name(String name) {
            this.predicates.add(mob -> mob.getCustomName() != null && mob.getCustomName().getString().equalsIgnoreCase(name));
            return this;
        }

        public @NotNull ModelCondition<T> ageRange(int minAge, int maxAge) {
            this.predicates.add(mob -> {
                int age = mob.getAge();
                return age >= minAge && age <= maxAge;
            });
            return this;
        }

        public @NotNull ModelCondition<T> custom(@NotNull Predicate<T> predicate) {
            this.predicates.add(predicate);
            return this;
        }

        public @NotNull Predicate<T> build() {
            return this.predicates.stream().reduce(x -> true, Predicate::and);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected record Builder<T extends ATAnimal<T>>(EntityRendererProvider.Context ctx, List<ModelEntry<T>> models) {
        public @NotNull Builder<T> addModel(int priority, @NotNull Supplier<ATAnimalModel<T>> supplier) {
            return this.addModel(priority, supplier, List.of(e -> true));
        }

        public @NotNull Builder<T> addModel(int priority, @NotNull Supplier<ATAnimalModel<T>> supplier, @NotNull Predicate<T> condition) {
            return this.addModel(priority, supplier, List.of(condition));
        }

        public @NotNull Builder<T> addModel(int priority, Supplier<ATAnimalModel<T>> supplier, @NotNull List<Predicate<T>> conditions) {
            if (supplier == null) {
                throw new IllegalArgumentException("Model supplier cannot be null");
            }
            this.models.add(new ModelEntry<>(priority, supplier, conditions));
            this.models.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
            return this;
        }

        public @NotNull Builder<T> addModelForBaby(int priority, @NotNull Supplier<ATAnimalModel<T>> supplier) {
            return this.addModel(priority, supplier, List.of(LivingEntity::isBaby));
        }
    }
}