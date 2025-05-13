package commands;

import managers.CollectionManager;
import managers.CommandManager;
import utility.Console;
import utility.ExecutionResponse;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class Info extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String arguments) {
        if (!arguments.isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        ZonedDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeS = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
        ZonedDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeS = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
        var s = "Сведения о коллекции:\n";
        s += " Тип: " + collectionManager.getCollection().getClass().toString() + "\n";
        s += " Количество элементов: " + collectionManager.getCollection().size() + "\n";
        s += " Дата последнего сохранения: " + lastSaveTimeS + "\n";
        s += " Дата последней инициализации: " + lastInitTimeS;
        return new ExecutionResponse(s);
    }
}
