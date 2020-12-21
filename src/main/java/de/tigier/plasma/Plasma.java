package de.tigier.plasma;



import net.fabricmc.api.ModInitializer;


public class Plasma implements ModInitializer {

    public static final String MOD_ID = "plasma";


 //test


    @Override
    public void onInitialize() {
         Registry.registerBlocks();
         Registry.registerItems();

         Registry.registerLoot();
    }
}
