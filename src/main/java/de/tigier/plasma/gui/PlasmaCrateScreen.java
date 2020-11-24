package de.tigier.plasma.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class PlasmaCrateScreen extends CottonInventoryScreen<PlasmaCrateController> {
    public PlasmaCrateScreen(PlasmaCrateController description, PlayerEntity player, Text title) {
        super(description, player, title);
    }
}
