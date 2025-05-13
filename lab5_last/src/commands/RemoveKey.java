package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class RemoveKey extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveKey(Console console, CollectionManager collectionManager) {
        super("remove_key null", "удалить элемент из коллекции по его ключу");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (argument.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        Long index = -1L;
        try {
            index = Long.parseLong(argument);
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID не распознан");
        }
        try {
            var d = collectionManager.getCollection().get(index);
            collectionManager.remove(index);
            collectionManager.update();
            return new ExecutionResponse("Person удалён");
        } catch (IndexOutOfBoundsException e) {
            return new ExecutionResponse(false, "index за границами допустимых значений");
        }
    }
}
