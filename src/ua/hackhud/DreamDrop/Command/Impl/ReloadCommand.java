package ua.hackhud.DreamDrop.Command.Impl;

import ua.hackhud.DreamDrop.Command.BaseCommand;
import org.bukkit.command.CommandSender;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class ReloadCommand extends BaseCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!validateSender(sender)) return true;

        plugin.getConfigManager().reload();
        plugin.getStorageManager().reload();
        MessageUtils.sendMessage(sender, PREFIX + "Плагин успешно перезагружен!");
        return true;
    }

    @Override
    public String getUsage() {
        return "/dreamdrop reload";
    }

    @Override
    public String getDescription() {
        return "Перезагрузить конфигурацию";
    }
}