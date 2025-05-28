
package commands;

import managers.CollectionManager;
import models.Ask;
import models.Person;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Обновление элемента
 *
 * @author Miroslav
 * @version 1.0
 */


public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        try {
            if (argument.isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

            Long personId;
            try {
                personId = Long.parseLong(argument.trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "ID не распознан");
            }

            Long key = collectionManager.getKeyByPersonId(personId);
            if (key == null) {
                return new ExecutionResponse(false, "Не существующий ID");
            }

            Person oldPerson = collectionManager.byId(key);
            if (oldPerson == null) {
                return new ExecutionResponse(false, "Элемент с таким ID не найден");
            }

            console.println("Создание нового Person:");
            Person newPerson = Ask.askPerson(console, personId);
            if (newPerson == null || !newPerson.validate()) {
                return new ExecutionResponse(false, "Поля не валидны! Person не создан");
            }

            collectionManager.getCollection().put(key, newPerson);
            collectionManager.update();
            return new ExecutionResponse("Обновлено");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена операции");
        }
    }
}
