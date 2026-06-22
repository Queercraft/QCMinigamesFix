package org.queercraft.QCMinigamesFix;

import org.bukkit.plugin.java.JavaPlugin;

public final class QCMinigamesFix extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new SpectatorWorldListener(this), this);
        getCommand("qcmf").setExecutor(new QCMinigamesFixCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
