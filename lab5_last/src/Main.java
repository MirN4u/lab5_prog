import managers.*;
import commands.*;
import models.*;
import utility.*;

public class Main {
    public static void main(String[] args) {
        var console = new StandartConsole();

        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        var dumpManager = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) {
            System.exit(1);
        }

        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("insert", new Insert(console, collectionManager));
            register("exit", new Exit(console));
            register("show", new Show(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("info", new Info(console, collectionManager));
            register("remove_key", new RemoveKey(console, collectionManager));
            register("remove_greater",new RemoveGreater(console, collectionManager));
            register("replace_if_greater", new RemoveGreater(console, collectionManager));
            register("remove_lower_key", new RemoveLower(console, collectionManager));
            register("filter_contains_name", new FilterContains(console, collectionManager));
            register("filter_starts_with_name", new FilterStarts(console, collectionManager));
            register("count_greater_than_location", new CountLocation(console, collectionManager));
            register("replace_if_greater", new Replace(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}