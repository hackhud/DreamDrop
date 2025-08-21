package ua.hackhud.DreamDrop.Command;

import ua.hackhud.DreamDrop.Command.Impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandFactory() {
        registerCommands();
    }

    private void registerCommands() {
        commands.put("reload", new ReloadCommand());
        commands.put("get", new GetCommand());
        commands.put("add", new AddCommand());
        commands.put("give", new GiveCommand());
        commands.put("perkedgive", new PerkedGiveCommand());
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName.toLowerCase());
    }

    public Map<String, Command> getAllCommands() {
        return new HashMap<>(commands);
    }
}