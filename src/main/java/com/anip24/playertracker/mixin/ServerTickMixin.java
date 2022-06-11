package com.anip24.playertracker.mixin;

import com.anip24.playertracker.ModConfig;
import com.anip24.playertracker.TrackerServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.SERVER)
@Mixin(MinecraftServer.class)
public class ServerTickMixin {

    @Shadow
    private PlayerManager playerManager;

    private final ModConfig config = TrackerServer.getConfig();

    @Shadow
    private int ticks;

    @Inject(at = @At("HEAD"), method = "tickWorlds(Ljava/util/function/BooleanSupplier;)V")
    private void tickInject(CallbackInfo info) {
        if (!config.enabled) return;

        if (ticks % TrackerServer.getConfig().frequency == 0) {
            List<ServerPlayerEntity> playerList = playerManager.getPlayerList();

            TrackerServer.CheckDateChange();

            for (ServerPlayerEntity player : playerList) {
                TrackerServer.LogPosition(player);
            }

            if (config.debugLogging && playerList.size() != 0) {
                System.out.println("Log tick called");
            }
        }
    }
}