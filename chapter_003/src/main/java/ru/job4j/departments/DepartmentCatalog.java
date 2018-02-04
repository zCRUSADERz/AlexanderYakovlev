package ru.job4j.departments;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Department catalog.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 05.02.2018
 */
public class DepartmentCatalog {
    private TreeMap<String, DepartmentCatalog> subCatalog;

    DepartmentCatalog() {
        subCatalog = new TreeMap<>();
    }

    /**
     * Add departments to catalog.
     * @param departments - departments to be added to catalog.
     */
    public void addDepartments(String[] departments) {
        for (String str : departments) {
            List<String> list = Arrays.asList(str.split(Pattern.quote("\\")));
            addDepartment(list.iterator());
        }
    }

    /**
     * Return directory of departments.
     * @param reverseOrder - if true, then return directory in reverse order.
     * @return - directory.
     */
    public String[] getCatalog(boolean reverseOrder) {
        List<String> list;
        list = getCatalog("", new ArrayList<>(), reverseOrder);
        return list.toArray(new String[0]);
    }

    private void addDepartment(Iterator<String> it) {
        if (it.hasNext()) {
            String department = it.next();
            if (subCatalog.containsKey(department)) {
                subCatalog.get(department).addDepartment(it);
            } else {
                DepartmentCatalog newDepartmentCatalog = new DepartmentCatalog();
                subCatalog.put(department, newDepartmentCatalog);
                newDepartmentCatalog.addDepartment(it);
            }
        }
    }

    private List<String> getCatalog(String previous, List<String> list, boolean reverseOrder) {
        NavigableMap<String, DepartmentCatalog> departments;
        if (reverseOrder) {
            departments = subCatalog.descendingMap();
        } else {
            departments = subCatalog;
        }
        for (Map.Entry<String, DepartmentCatalog> entry : departments.entrySet()) {
            String department = previous + entry.getKey();
            list.add(department);
            department += "\\";
            entry.getValue().getCatalog(department, list, reverseOrder);
        }
        return list;
    }
}
