package utility;

import java.util.Scanner;

/**
 * Консоль для ввода и вывода команд
 *
 * @author Miroslav
 * @version 1.0
 */

public interface Console {
    void print(Object obj);

    void println(Object obj);

    String readln();

    boolean isCanReadln();

    void printError(Object obj);

    void printTable(Object obj1, Object obj2);

    void prompt();

    String getPrompt();

    void selectFileScanner(Scanner obj);

    void selectConsoleScanner();
}
