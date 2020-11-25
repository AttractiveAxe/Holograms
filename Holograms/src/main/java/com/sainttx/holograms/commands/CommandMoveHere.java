package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMoveHere implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandMoveHere(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can move holograms.");
        } else if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram movehere <name>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

            if (hologram == null) {
                sender.sendMessage(ChatColor.RED + "Hologram " + hologramName + " does not exist");
            } else {
                Player player = (Player) sender;
                hologram.despawn();
                hologram.teleport(player.getLocation());
                hologram.spawn();
                plugin.getHologramManager().saveHologram(hologram);
                sender.sendMessage(ChatColor.GREEN + "Moved " + hologramName + " to your current location");
            }
        }

        return true;
    }
}