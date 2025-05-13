package commands;

import managers.CollectionManager;
import models.Person;
import utility.Console;
import utility.ExecutionResponse;

import java.util.Map;
import java.util.TreeMap;

public class FilterContains extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterContains(Console console, CollectionManager collectionManager) {
        super("filter_contains_name name", "вывести элементы, значение поля name которых содержит заданную подстроку");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (argument.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        String name = argument.trim();
        Map<Long, Person> collection = new TreeMap<>();
        for (var e : collectionManager.getCollection().values()) {
            if (e.getName().equals(name)) {
                collection.put(e.getId(), e);
            }
        }
        return new ExecutionResponse(true, collection.toString());
    }
}
