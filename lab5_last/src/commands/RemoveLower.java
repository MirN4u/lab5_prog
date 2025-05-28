package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

import java.util.TreeMap;


/**
 * Команда удаления от меньшего значения
 *
 * @author Miroslav
 * @version 1.0
 */

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
        // Проверка аргументов
        if (argument == null || argument.trim().isEmpty()) {
            return new ExecutionResponse(false,
                    "Требуется указать ключ\nИспользование: '" + getName() + "'");
        }

        // Парсинг ключа
        long targetKey;
        try {
            targetKey = Long.parseLong(argument.trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ключ должен быть числом");
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
                if (key < targetKey) {
                    if (collectionManager.remove(key)) {
                        removedCount++;
                    }
                } else {
                    break; // TreeMap отсортирован, можно прерваться
                }
            }

            if (removedCount > 0) {
                collectionManager.update(); // Сохраняем изменения один раз
                return new ExecutionResponse(true,
                        "Удалено элементов: " + removedCount);
            } else {
                return new ExecutionResponse(true,
                        "Не найдено элементов с ключами меньше " + targetKey);
            }
        } catch (Exception e) {
            return new ExecutionResponse(false,
                    "Ошибка при удалении: " + e.getMessage());
        }
    }
}

