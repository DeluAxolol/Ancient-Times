package com.delu.ancienttimes.client.renderer;

import com.delu.ancienttimes.client.model.CalciferopteraModel;
import com.delu.ancienttimes.server.entity.Calciferoptera;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class CalciferopteraRenderer extends ATAnimalRenderer<Calciferoptera> {
    public CalciferopteraRenderer(@NotNull EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    protected void initModels(@NotNull Builder<Calciferoptera> builder) {
        builder.addModel(1, CalciferopteraModel::new);
    }
}