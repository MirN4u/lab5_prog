package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда удаления по ключу
 *
 * @author Miroslav
 * @version 1.0
 */


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
        // Проверка аргументов
        if (argument == null || argument.trim().isEmpty()) {
            return new ExecutionResponse(false,
                    "Требуется указать ключ элемента\nИспользование: '" + getName() + "'");
        }

        // Парсинг ключа
        long key;
        try {
            key = Long.parseLong(argument.trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ключ должен быть числом");
        }

        // Проверка существования элемента
        if (!collectionManager.getCollection().containsKey(key)) {
            return new ExecutionResponse(false, "Элемент с ключом " + key + " не найден");
        }

        // Удаление элемента
        try {
            boolean removed = collectionManager.remove(key);
            if (removed) {
                return new ExecutionResponse(true,
                        "Элемент с ключом " + key + " успешно удалён");
            } else {
                return new ExecutionResponse(false,
                        "Не удалось удалить элемент с ключом " + key);
            }
        } catch (Exception e) {
            return new ExecutionResponse(false,
                    "Ошибка при удалении: " + e.getMessage());
        }
    }
}
