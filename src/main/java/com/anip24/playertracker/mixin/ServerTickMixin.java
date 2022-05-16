package com.anip24.playertracker.mixin;

import com.anip24.playertracker.ModConfig;
import com.anip24.playertracker.Tracker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MinecraftServer.class)
public class ServerTickMixin {

    @Shadow
    private PlayerManager playerManager;

    private final ModConfig config = Tracker.getConfig();

    @Shadow
    private int ticks;

    //    @Environment(EnvType.SERVER)
    @Inject(at = @At("HEAD"), method = "tickWorlds(Ljava/util/function/BooleanSupplier;)V")
    private void tickInject(CallbackInfo info) {
        if (!config.enabled) return;

        if (ticks % Tracker.getConfig().frequency == 0) {
            List<ServerPlayerEntity> playerList = playerManager.getPlayerList();
//            playerManager.getPlayerList().forEach(TrackerTickHandler::tick);

            for (ServerPlayerEntity player : playerList) {
                Tracker.LogPosition(player);
            }

            if (config.debugLogging && playerList.size() > 0) {
                System.out.println("Log tick called");
            }
        }
    }
}