package commands;

import managers.CollectionManager;
import models.Ask;
import utility.Console;
import utility.ExecutionResponse;

public class Replace extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Replace(Console console, CollectionManager collectionManager) {
        super("replace_if_greater", "заменить значение по ключу, если новое значение больше старого");
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
                if (person.getLocation().getNum() + person.getCoordinates().getNum() >
                        old.getLocation().getNum() + old.getCoordinates().getNum()) {
                    collectionManager.remove(old.getId());
                    collectionManager.add(person,old.getId());
                    collectionManager.update();
                } else {
                    return new ExecutionResponse(false, "Новый Person меньше старого, Person не создан");
                }
            } else {
                return new ExecutionResponse(false, "Поля не валидны! Person не создан");
            }
            return new ExecutionResponse("Обновлено");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Поля не валидны! Person не создан)");
        }
    }
}
