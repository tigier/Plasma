package de.tigier.plasma;

import net.fabricmc.api.ClientModInitializer;

public class PlasmaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Registry.registerGui();
    }
}
