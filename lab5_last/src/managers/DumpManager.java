package managers;

import au.com.bytecode.opencsv.*;
import models.Person;
import utility.Console;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    private String collection2CSV(Collection<Person> collection) {
        try {
            StringWriter sw = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(sw, ';');
            for (var e : collection) {
                csvWriter.writeNext(Person.toArray(e));
            }
            String csv = sw.toString();
            return csv;
        } catch (Exception e) {
            console.printError("Ошибка сериализации");
            return null;
        }
    }

    public void writeCollection(Collection<Person> collection) {
        OutputStreamWriter writer = null;
        try {
            var csv = collection2CSV(collection);
            if (csv == null) return;
            writer = new OutputStreamWriter(new FileOutputStream(fileName));
            try {
                writer.write(csv);
                writer.flush();
                console.println("Коллекция успешна сохранена в файл!");
            } catch (IOException e) {
                console.printError("Неожиданная ошибка сохранения");
            }
        } catch (FileNotFoundException | NullPointerException e) {
            console.printError("Файл не найден");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                console.printError("Ошибка закрытия файла");
            }
        }
    }

    private LinkedList<Person> CSV2collection(String s) {
        try {
            StringReader sr = new StringReader(s);
            CSVReader csvReader = new CSVReader(sr, ';');
            LinkedList<Person> ds = new LinkedList<Person>();
            String[] record = null;
            while ((record = csvReader.readNext()) != null) {
                Person d = Person.fromArray(record);
                if (d.validate())
                    ds.add(d);
                else
                    console.printError("Файл с колекцией содержит не действительные данные");
            }
            csvReader.close();
            return ds;
        } catch (Exception e) {
            console.printError("Ошибка десериализации");
            return null;
        }
    }

    public void readCollection(Collection<Person> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new Scanner(new File(fileName))) {
                var s = new StringBuilder("");
                while (fileReader.hasNextLine()) {
                    s.append(fileReader.nextLine());
                    s.append("\n");
                }
                collection.clear();
                for (var e : CSV2collection(s.toString()))
                    collection.add(e);
                if (collection != null) {
                    console.println("Коллекция успешна загружена!");
                    return;
                } else
                    console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        collection = new LinkedList<Person>();
    }
}
