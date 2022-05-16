package com.anip24.playertracker.mixin;

import com.anip24.playertracker.ModConfig;
import com.anip24.playertracker.Tracker;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private final ModConfig config = Tracker.getConfig();

    @Shadow
    private int ticks = 0;

    @Inject(method = "renderEntity", at = @At(value = "RETURN"))
    private void renderEntity(Entity entity, double x, double y, double z, float g, MatrixStack matrix, VertexConsumerProvider v, CallbackInfo info) {
        if (!config.enabled) return;

        if (entity instanceof PlayerEntity) {
            Tracker.RegisterPlayer((PlayerEntity) entity);
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tick(CallbackInfo ci) {
        if (!config.enabled) return;

        if (ticks % Tracker.getConfig().frequency == 0) {
            Tracker.LogPositions();
            if (config.debugLogging)
                System.out.println("Log tick called");
        }
    }

//    @Inject(method = "render", at = @At(value = "RETURN"))
//    private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix, CallbackInfo info) {
//
//        if (tick > 50) {
//            tick = 0;
//            Tracker.LogPositions();
//            System.out.println("ticked");
//        } else {
//            tick++;
//        }
//    }
}