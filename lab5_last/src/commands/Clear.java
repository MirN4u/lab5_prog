package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        Long x = 0L;
        while (collectionManager.getCollection().size() > 0) {
            collectionManager.getCollection().remove(x++);
        }
        collectionManager.update();
        return new ExecutionResponse("Коллекция очищена");
    }
}

