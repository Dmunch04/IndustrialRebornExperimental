package me.munchii.industrialrebornexperimental.config;

import reborncore.common.config.Config;

public class IndustrialRebornConfig {
    @Config(config = "generators", category = "death_generator", key = "DeathGeneratorMaxOutput", comment = "Death Generator Max Output (Energy per tick)")
    public static int deathGeneratorMaxOutput = 64;

    @Config(config = "generators", category = "death_generator", key = "DeathGeneratorMaxEnergy", comment = "Death Generator Max Energy")
    public static int deathGeneratorMaxEnergy = 40_000;

    @Config(config = "generators", category = "death_generator", key = "DeathGeneratorEnergyPerTick", comment = "Death Generator Energy Per Tick")
    public static int deathGeneratorEnergyPerTick = 20;

    @Config(config = "machines", category = "soul_fuser", key = "SoulFuserInput", comment = "Soul Fuser Max Input (Energy per tick")
    public static int soulFuserMaxInput = 128;

    @Config(config = "machines", category = "soul_fuser", key = "SoulFuserMaxEnergy", comment = "Soul Fuser Max Energy")
    public static int soulFuserMaxEnergy = 100_000;

    @Config(config = "machines", category = "powered_spawner", key = "PoweredSpawnerInput", comment = "Powered Spawner Max Input (Energy per tick")
    public static int poweredSpawnerMaxInput = 512;

    @Config(config = "machines", category = "powered_spawner", key = "PoweredSpawnerMaxEnergy", comment = "Powered Spawner Max Energy")
    public static int poweredSpawnerMaxEnergy = 1_000_000;

    @Config(config = "machines", category = "powered_spawner", key = "PoweredSpawnerEnergyPerSpawn", comment = "Powered Spawner Energy Per Spawn")
    public static int poweredSpawnerEnergyPerSpawn = 512;
}
