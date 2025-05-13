
package commands;

import managers.CollectionManager;
import models.Ask;
import utility.Console;
import utility.ExecutionResponse;

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
            Long id = -1L;
            try {
                id = Long.parseLong(argument.trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "ID не распознан");
            }
            var old = collectionManager.byId(id);
            if (old == null || !collectionManager.getCollection().containsValue(old)) {
                return new ExecutionResponse(false, "Не существующий ID");
            }

            console.println("Создание нового Person:");
            var person = Ask.askPerson(console, old.getId());
            if (person != null && person.validate()) {
                collectionManager.remove(old.getId());
                collectionManager.add(person,old.getId());
                collectionManager.update();
                return new ExecutionResponse("Обновлено");
            } else {
                return new ExecutionResponse(false, "Поля не валидны! Person не создан");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Поля не валидны! Person не создан)");
        }
    }
}
