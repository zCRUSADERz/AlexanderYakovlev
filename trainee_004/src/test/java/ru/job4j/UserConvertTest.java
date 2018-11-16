package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserConvertTest {

    @Test
    public void convertNamesToUsers() {
        List<String> names = Arrays.asList("Petr", "Nick", "Ban");
        UserConvert users = new UserConvert();
        List<UserConvert.User> result = users.convert(names, UserConvert.User::new);
        assertThat(
                result,
                is(Arrays.asList(
                        new UserConvert.User("Petr"),
                        new UserConvert.User("Nick"),
                        new UserConvert.User("Ban")
                        )
                )
        );
    }
}