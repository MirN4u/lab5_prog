package commands;

import managers.CollectionManager;
import models.Ask;
import models.Person;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда с заменой объекта от значений
 *
 * @author Miroslav
 * @version 1.0
 */

public class Replace extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Replace(Console console, CollectionManager collectionManager) {
        super("replace_if_greater null {element} ", "заменить значение по ключу, если новое значение больше старого");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        try {
            if (argument.isEmpty()) {
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }

            // Парсим ключ (не id Person!)
            Long key;
            try {
                key = Long.parseLong(argument.trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "Ключ не распознан");
            }

            // Получаем старый Person по ключу
            Person oldPerson = collectionManager.byId(key);
            if (oldPerson == null) {
                return new ExecutionResponse(false, "Элемент с ключом " + key + " не найден");
            }

            console.println("Создание нового Person:");
            // Создаем нового Person с тем же id, что и у старого
            Person newPerson = Ask.askPerson(console, oldPerson.getId());

            if (newPerson == null || !newPerson.validate()) {
                return new ExecutionResponse(false, "Поля не валидны! Person не создан");
            }

            // Сравниваем по сумме координат и локации
            double oldSum = oldPerson.getCoordinates().getNum() + oldPerson.getLocation().getNum();
            double newSum = newPerson.getCoordinates().getNum() + newPerson.getLocation().getNum();

            if (newSum > oldSum) {
                // Заменяем элемент по тому же ключу
                collectionManager.getCollection().put(key, newPerson);
                return new ExecutionResponse("Элемент с ключом " + key + " успешно заменен");
            } else {
                return new ExecutionResponse(false, "Новый Person не больше старого. Замена не выполнена.");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена операции (введено 'exit')");
        }
    }
}