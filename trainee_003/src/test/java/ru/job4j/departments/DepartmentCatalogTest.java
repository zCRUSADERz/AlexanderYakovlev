package ru.job4j.departments;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Department catalog test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 05.02.2018
 */
public class DepartmentCatalogTest {

    @Test
    public void whenGetCatalogThenReturnArrayOfStrings() {
        DepartmentCatalog  catalog = new DepartmentCatalog();
        String[] source = new String[]{
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        catalog.addDepartments(source);
        String[] result = catalog.getCatalog(false);
        String[] expected = new String[] {
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetCatalogInReverseOrderThenReturnArrayOfStrings() {
        DepartmentCatalog  catalog = new DepartmentCatalog();
        String[] source = new String[]{
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        catalog.addDepartments(source);
        String[] result = catalog.getCatalog(true);
        String[] expected = new String[] {
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1", "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        };
        assertThat(result, is(expected));
    }
}