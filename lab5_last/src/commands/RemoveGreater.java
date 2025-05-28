package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

import java.util.TreeMap;

/**
 * Команда удаления элементов от большего значения
 *
 * @author Miroslav
 * @version 1.0
 */

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
        // Проверка аргументов
        if (argument == null || argument.trim().isEmpty()) {
            return new ExecutionResponse(false,
                    "Требуется указать ID элемента\nИспользование: '" + getName() + "'");
        }

        // Парсинг ID
        long targetId;
        try {
            targetId = Long.parseLong(argument.trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID должен быть числом");
        }

        // Проверка пустой коллекции
        TreeMap<Long, ?> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            return new ExecutionResponse(true, "Коллекция пуста - нечего удалять");
        }

        // Удаление элементов
        int removedCount = 0;
        try {
            // Создаем копию ключей для безопасного удаления
            for (Long key : new TreeMap<>(collection).keySet()) {
                if (key > targetId) {
                    if (collectionManager.remove(key)) {
                        removedCount++;
                    }
                }
            }

            if (removedCount > 0) {
                collectionManager.update(); // Сохраняем изменения один раз
                return new ExecutionResponse(true,
                        "Удалено элементов: " + removedCount);
            } else {
                return new ExecutionResponse(true,
                        "Не найдено элементов с ID больше " + targetId);
            }
        } catch (Exception e) {
            return new ExecutionResponse(false,
                    "Ошибка при удалении: " + e.getMessage());
        }
    }
}