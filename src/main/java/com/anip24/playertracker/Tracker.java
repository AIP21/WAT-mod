package com.anip24.playertracker;

import com.anip24.playertracker.mixin.MinecraftServerAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Tracker implements ModInitializer {
    public static File overworldLogFile;
    public static File netherLogFile;
    public static File endLogFile;
    private static Config config;
    private static File basePath;
    private static int prevDay = 0;

//    private static MinecraftClient client;

    public static List<PlayerEntity> trackedPlayers = new ArrayList<>();

    public static void AddToList(PlayerEntity toAdd) {
        if (!trackedPlayers.contains(toAdd)) {
            trackedPlayers.add((toAdd));
            System.out.println("Player is not already being tracked: " + toAdd);
        }
    }

    public static void CreateFile(MinecraftClient client) {
        try {
            String worldName = "";

            if (client != null) {
                if (client.isInSingleplayer()) {
                    worldName = ((MinecraftServerAccessor) client.getServer()).tracker_getSession().getDirectoryName();
                } else {
                    worldName = client.getCurrentServerEntry().name;
                }
            }

            String filename = worldName + "-overworld-log-" + LocalDate.now() + ".txt";

            overworldLogFile = new File(basePath, filename);
            if (!overworldLogFile.exists() && overworldLogFile.createNewFile()) {
                System.out.println("Overworld log file created: " + filename);
            } else {
                System.out.println("Overworld log file with name " + filename + " already exists.");
            }

            filename = worldName + "-nether-log-" + LocalDate.now() + ".txt";

            netherLogFile = new File(basePath, filename);
            if (!netherLogFile.exists() && netherLogFile.createNewFile()) {
                System.out.println("Nether log file created: " + filename);
            } else {
                System.out.println("Nether log file with name " + filename + " already exists.");
            }

            filename = worldName + "-end-log-" + LocalDate.now() + ".txt";

            endLogFile = new File(basePath, filename);
            if (!endLogFile.exists() && endLogFile.createNewFile()) {
                System.out.println("End log file created: " + filename);
            } else {
                System.out.println("End log file with name " + filename + " already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred in creating a log file: " + e);
        }
    }

    public static void LogPosition(PlayerEntity player) {
        if (prevDay != LocalDate.now().getDayOfYear()) {
            CreateFile(MinecraftClient.getInstance());
            prevDay = LocalDate.now().getDayOfYear();
        }

        RegistryKey<World> world = player.world.getRegistryKey();

        File targetFile;
        if (world == World.NETHER) {
            targetFile = netherLogFile;
        } else if (world == World.END) {
            targetFile = endLogFile;
        } else {
            targetFile = overworldLogFile;
        }

        try {
            FileWriter myWriter = new FileWriter(targetFile, true);
            myWriter.write(String.format("%s; %s; (%s);\n",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString(),
                    player.getEntityName(),
                    player.getBlockPos().toShortString()));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in logging the player position." + e);
            e.printStackTrace();
        }
    }

    public static void LogPositions() {
        for (PlayerEntity player : trackedPlayers) {
            RegistryKey<World> world = player.world.getRegistryKey();
            if (prevDay != LocalDate.now().getDayOfYear()) {
                CreateFile(MinecraftClient.getInstance());
                prevDay = LocalDate.now().getDayOfYear();
            }

            File targetFile;
            if (world == World.NETHER) {
                targetFile = netherLogFile;
            } else if (world == World.END) {
                targetFile = endLogFile;
            } else {
                targetFile = overworldLogFile;
            }

            try {
                FileWriter myWriter = new FileWriter(targetFile, true);
                myWriter.write(String.format("%s; %s; (%s);\n",
                        LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString(),
                        player.getEntityName(),
                        player.getBlockPos().toShortString()));
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred in logging the player position." + e);
                e.printStackTrace();
            }
        }
    }

    public static Config getConfig() {
        return config;
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(Config.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Config.class).getConfig();
        basePath = FabricLoader.getInstance().getGameDir().resolve("playerTracker").toFile();
        prevDay = LocalDate.now().getDayOfYear();

        File containerFolder = new File(basePath.getPath());
        if (!containerFolder.exists()) {
            containerFolder.mkdirs();
            System.out.println("Successfully created log container folder");
        }

        ClientPlayConnectionEvents.JOIN.register((handler, sender, _client) -> {
            Tracker.CreateFile(_client);
        });
    }
}