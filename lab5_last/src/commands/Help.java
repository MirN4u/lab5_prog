package commands;

import managers.CommandManager;
import utility.Console;
import utility.ExecutionResponse;

import java.util.stream.Collectors;

/**
 * Вывод доступных команд
 *
 * @author Miroslav
 * @version 1.0
 */

public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public ExecutionResponse apply(String arguments) {
        if (!arguments.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }
}
