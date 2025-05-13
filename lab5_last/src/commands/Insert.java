package commands;

import managers.CollectionManager;
import models.Ask;
import models.Person;
import utility.Console;
import utility.ExecutionResponse;

public class Insert extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Insert(Console console, CollectionManager collectionManager) {
        super("insert null{element}", "добавить новый элемент с заданным ключом");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String argument) {
        /*try{
            if(!argument.isEmpty()) return new ExecutionResponse(false,
                    "Неправильное количество аргументов!\nИспользование:'"+ getName() + "'");
            console.println("* Создание нового Person:");
            Person person = Ask.askPerson(console, collectionManager.getFreeId());

            if (person!= null && person.validate()){
                collectionManager.add(person);
                return new ExecutionResponse("Person успешно добавлен!");
            } else return new ExecutionResponse(false,"Поля person не валидны! Person не создан");
        }catch (Ask.AskBreak e){
            return new ExecutionResponse(false,"Отмена");
        }*/
        try {
            if (argument.isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            Long id = -1L;
            try {
                id = Long.parseLong(argument.trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "ID не распознан");
            }
            if (collectionManager.getCollection().containsKey(id)) {
                return new ExecutionResponse(false, "ID существует, попробуйте другой");
            }
            console.println("Создание нового Person:");
            var person = Ask.askPerson(console, collectionManager.getFreeId());
            if (person != null && person.validate()) {
                collectionManager.add(person,id);
                collectionManager.update();
                return new ExecutionResponse("Person успешно добавлен!");
            } else {
                return new ExecutionResponse(false, "Поля не валидны! Person не создан");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Поля не валидны! Person не создан");
        }
    }
}
