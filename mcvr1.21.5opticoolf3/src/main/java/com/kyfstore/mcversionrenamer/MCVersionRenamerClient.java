package com.kyfstore.mcversionrenamer;

import com.kyfstore.mcversionrenamer.customlibs.yacl.MCVersionRenamerConfig;
import com.kyfstore.mcversionrenamer.data.MCVersionRenamerPublicData;
import com.kyfstore.mcversionrenamer.version.VersionCheckerApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;

@Environment(EnvType.CLIENT)
public class MCVersionRenamerClient implements ClientModInitializer {

    private static final VersionCheckerApi versionChecker = new VersionCheckerApi();
    private static boolean hasCheckedVersion = false;

    // Use string concatenation rather than String.format() for efficiency

    @Override
    public void onInitializeClient() {
        versionChecker.onEnable(this);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Move these to the start of the tick to avoid redundant assignments
            if (!hasCheckedVersion && client.currentScreen instanceof TitleScreen) {
                hasCheckedVersion = true;
                versionChecker.checkVersion(client); // Check version only once
            }

            // Update MCVersionPublicData only if needed
            if (MCVersionRenamerPublicData.f3Text != MCVersionRenamerConfig.f3Text) {
                MCVersionRenamerPublicData.f3Text = MCVersionRenamerConfig.f3Text;
            }

            // Toggle button visibility only if it changes
            if (MCVersionRenamerConfig.buttonEnabled != MCVersionRenamerPublicData.customButtonIsVisible) {
                setButtonVisibility(MCVersionRenamerConfig.buttonEnabled);
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            MCVersionRenamerConfig.HANDLER.save();
            MCVersionRenamer.LOGGER.shutdown();
        });
    }

    public static void setButtonVisibility(boolean type) {
        MCVersionRenamerPublicData.customButtonIsVisible = type;
    }
}
