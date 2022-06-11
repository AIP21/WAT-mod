package com.anip24.playertracker.mixin;

import com.anip24.playertracker.ModConfig;
import com.anip24.playertracker.TrackerClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    private final ModConfig config = TrackerClient.getConfig();

    @Shadow
    private int ticks = 0;

    @Inject(method = "renderEntity", at = @At(value = "RETURN"))
    private void renderEntity(Entity entity, double x, double y, double z, float g, MatrixStack matrix, VertexConsumerProvider v, CallbackInfo info) {
        if (!config.enabled) return;

        if (entity instanceof PlayerEntity) {
            TrackerClient.RegisterPlayer((PlayerEntity) entity);
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tick(CallbackInfo ci) {
        if (!config.enabled) return;

        if (ticks % TrackerClient.getConfig().frequency == 0) {
            TrackerClient.CheckDateChange();

            TrackerClient.LogPositions();

            if (config.debugLogging && TrackerClient.trackedPlayers.size() != 0)
                System.out.println("Log tick called");
        }
    }
}