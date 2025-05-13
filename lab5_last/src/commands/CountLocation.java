package commands;

import managers.CollectionManager;
import models.Location;
import models.Person;
import utility.Console;
import utility.ExecutionResponse;

import java.util.Map;
import java.util.TreeMap;

public class CountLocation extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CountLocation(Console console, CollectionManager collectionManager) {
        super("count_greater_than_location location", "вывести количество элементов, значение поля location которых больше заданного");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (argument.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        Integer loc = Integer.parseInt(argument.trim());
        Map<Long, Person> collection = new TreeMap<>();
        for (var e : collectionManager.getCollection().values()) {
            if ((e.getLocation().getX().intValue() + e.getLocation().getY()) > loc) {
                collection.put(e.getId(), e);
            }
        }
        return new ExecutionResponse(true, collection.toString());
    }
}
