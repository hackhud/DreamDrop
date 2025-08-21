package ua.hackhud.DreamDrop.Command;

import org.bukkit.command.CommandSender;

public interface Command {
    boolean execute(CommandSender sender, String args[]);
    String getUsage();
    String getDescription();
}
