package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class RemoveLower extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveLower(Console console, CollectionManager collectionManager) {
        super("remove_lower_key null", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
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
            var с = collectionManager.getCollection().firstKey();
            while (с < index) {
                collectionManager.remove(с);
                collectionManager.update();
                с = collectionManager.getCollection().firstKey();
            }
            return new ExecutionResponse("Элементы удалены");
        } catch (IndexOutOfBoundsException e) {
            return new ExecutionResponse(false, "index за границами допустимых значений");
        }
    }
}


