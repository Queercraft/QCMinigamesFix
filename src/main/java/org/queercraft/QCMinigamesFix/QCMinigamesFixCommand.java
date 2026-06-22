package org.queercraft.QCMinigamesFix;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class QCMinigamesFixCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public QCMinigamesFixCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Component.text("Usage: /" + label + " <reload|enable|disable|status>", NamedTextColor.RED));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                plugin.reloadConfig();
                sender.sendMessage(Component.text("QCMinigamesFix config reloaded.", NamedTextColor.GREEN));
            }
            case "enable" -> {
                plugin.getConfig().set("enabled", true);
                plugin.saveConfig();
                sender.sendMessage(Component.text("Spectator world restriction enabled.", NamedTextColor.GREEN));
            }
            case "disable" -> {
                plugin.getConfig().set("enabled", false);
                plugin.saveConfig();
                sender.sendMessage(Component.text("Spectator world restriction disabled.", NamedTextColor.YELLOW));
            }
            case "status" -> {
                boolean enabled = plugin.getConfig().getBoolean("enabled", true);
                sender.sendMessage(Component.text("Spectator world restriction is currently "
                        + (enabled ? "enabled" : "disabled") + ".", NamedTextColor.AQUA));
            }
            default -> sender.sendMessage(Component.text("Usage: /" + label + " <reload|enable|disable|status>", NamedTextColor.RED));
        }

        return true;
    }
}
