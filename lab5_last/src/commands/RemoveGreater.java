package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class RemoveGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
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
            var с = collectionManager.getCollection().lastKey();
            while (с > index) {
                try {
                    collectionManager.remove(с);
                    collectionManager.update();
                    с = collectionManager.getCollection().lastKey();
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
            return new ExecutionResponse("Элементы удалены");
        } catch (IndexOutOfBoundsException e) {
            return new ExecutionResponse(false, "index за границами допустимых значений");
        }
    }
}
