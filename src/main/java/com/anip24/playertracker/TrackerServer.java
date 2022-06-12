package com.anip24.playertracker;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TrackerServer implements DedicatedServerModInitializer {
    public static File overworldLogFile;
    public static File netherLogFile;
    public static File endLogFile;
    private static ModConfig config;
    private static File basePath;
    private static int prevDay = 0;

    public static void CreateFile() {
        try {
            String filename = "server-overworld-log-" + LocalDate.now() + ".txt";

            overworldLogFile = new File(basePath, filename);
            if (!overworldLogFile.exists() && overworldLogFile.createNewFile()) {
                if (config.debugLogging)
                    System.out.println("Overworld log file created: " + filename);
            } else {
                if (config.debugLogging)
                    System.out.println("Overworld log file with name " + filename + " already exists.");
            }

            filename = "server-nether-log-" + LocalDate.now() + ".txt";

            netherLogFile = new File(basePath, filename);
            if (!netherLogFile.exists() && netherLogFile.createNewFile()) {
                if (config.debugLogging)
                    System.out.println("Nether log file created: " + filename);
            } else {
                if (config.debugLogging)
                    System.out.println("Nether log file with name " + filename + " already exists.");
            }

            filename = "server-end-log-" + LocalDate.now() + ".txt";

            endLogFile = new File(basePath, filename);
            if (!endLogFile.exists() && endLogFile.createNewFile()) {
                if (config.debugLogging)
                    System.out.println("End log file created: " + filename);
            } else {
                if (config.debugLogging)
                    System.out.println("End log file with name " + filename + " already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred in creating a log file: ");
            e.printStackTrace();
        }
    }

    public static void LogPosition(PlayerEntity player) {
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
            System.out.println("An error occurred in logging the player position: ");
            e.printStackTrace();
        }
    }

    public static void CheckDateChange() {
        if (prevDay != LocalDate.now().getDayOfYear()) {
            CreateFile();
            prevDay = LocalDate.now().getDayOfYear();
        }
    }

    public static ModConfig getConfig() {
        return config;
    }

    @Override
    public void onInitializeServer() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        basePath = FabricLoader.getInstance().getGameDir().resolve("playerTracker").toFile();
        prevDay = LocalDate.now().getDayOfYear();

        File containerFolder = new File(basePath.getPath());

        if (!containerFolder.exists()) {
            boolean result = containerFolder.mkdirs();
            if (config.debugLogging)
                System.out.println(result ? "Successfully created" : "Failed to create" + " the log container folder");
        }

        CreateFile();
    }
}