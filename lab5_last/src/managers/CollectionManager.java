
package managers;

import models.Person;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Класс менеджер для управления коллекцией
 *
 * @author Miroslav
 * @version 1.0
 */

public class CollectionManager {
    private Long currentId = 1L;
    private Map<Long, Person> map = new TreeMap<>();
    private ZonedDateTime lastInitTime;
    private ZonedDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    public TreeMap<Long, Person> getCollection() {
        return (TreeMap<Long, Person>) map;
    }

    public ZonedDateTime getLastInitTime() {
        return lastInitTime;
    }

    public ZonedDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public void saveCollection() {
        dumpManager.writeCollection(map.values());
        lastSaveTime = ZonedDateTime.now();
    }

    public Person byId(Long id) {
        return map.get(id);
    }

    public boolean isСontain(Person e) {
        return e == null || byId(e.getId()) != null;
    }

    public Long getFreeId() {
        long id = 1;
        // Проверяем все ID в коллекции
        while (isIdUsed(id)) {
            id++;
        }
        return id;
    }

    private boolean isIdUsed(long id) {
        return map.values().stream()
                .anyMatch(person -> person.getId() == id);
    }

    public void resetCurrentId() {
        this.currentId = 1L;
    }

    public boolean add(Person d,Long currentId) {
        if (isСontain(d)) return false;
        map.put(currentId, d);
        update();
        return true;
    }

    public boolean remove(Long id) {
        var a = byId(id);
        if (a == null) return false;
        map.remove(id);
        update();
        return true;
    }

    public Long getKeyByPersonId(Long personId) {
        for (Map.Entry<Long, Person> entry : map.entrySet()) {
            if (entry.getValue().getId().equals(personId)) {
                return entry.getKey();
            }
        }
        return null; // если не найден
    }

    public void update() {
        Map<Long, Person> map = new TreeMap<>();
    }

    public boolean loadCollection() {
        LinkedList<Person> collection = new LinkedList<Person>();
        dumpManager.readCollection(collection);
        lastInitTime = ZonedDateTime.now();
        for (Person person : collection) {
            map.put(getFreeId(), person);
        }
        update();
        return true;
    }

    @Override
    public String toString() {
        if (map.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (Long key : map.keySet()) {
            info.append("key: "+ key+ "\n" + map.get(key) + "\n\n");
        }
        return info.toString().trim();
    }
}
