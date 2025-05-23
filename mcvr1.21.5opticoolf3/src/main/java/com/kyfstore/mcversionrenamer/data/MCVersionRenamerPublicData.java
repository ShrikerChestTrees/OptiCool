package com.kyfstore.mcversionrenamer.data;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;

public class MCVersionRenamerPublicData {

    public static String getMinecraftVersion() {
        Version version = FabricLoader.getInstance().getModContainer("minecraft")
                .orElseThrow()
                .getMetadata()
                .getVersion();
        return version.getFriendlyString();
    }

    public static String defaultF3Text = String.format("Minecraft 1.21.5 (1.21.5 OptiCool 1.0_B1_pre2/vanilla/modified)");


    public static String f3Text = defaultF3Text;
    public static boolean customButtonIsVisible = false;

    public static boolean modMenuIsLoaded = false;
    public static boolean fancyMenuIsLoaded = false;
}