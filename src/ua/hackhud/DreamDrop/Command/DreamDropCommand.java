package ua.hackhud.DreamDrop.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class DreamDropCommand implements CommandExecutor {

    private static final String PREFIX = "&e>> DreamDrop: ";
    private static final String ERROR_PREFIX = "&c>> DreamDrop: ";

    private final CommandFactory commandFactory = new CommandFactory();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        ua.hackhud.DreamDrop.Command.Command cmd = commandFactory.getCommand(subCommand);

        if (cmd == null) {
            MessageUtils.sendMessage(sender, ERROR_PREFIX + "Неизвестная команда.");
            sendUsage(sender);
            return true;
        }

        String[] commandArgs = new String[args.length - 1];
        System.arraycopy(args, 1, commandArgs, 0, commandArgs.length);

        try {
            return cmd.execute(sender, commandArgs);
        } catch (IllegalArgumentException e) {
            MessageUtils.sendMessage(sender, ERROR_PREFIX + e.getMessage());
            return true;
        }
    }

    private void sendUsage(CommandSender sender) {
        MessageUtils.sendMessage(sender, PREFIX + "&7Доступные команды:");

        commandFactory.getAllCommands().forEach((name, cmd) -> {
            MessageUtils.sendMessage(sender, "&7/dreamdrop " + name + " - " + cmd.getDescription());
        });
    }
}