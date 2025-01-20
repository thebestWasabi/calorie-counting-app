package ru.javawebinar.topjava;

import org.hamcrest.Matcher;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public class MatcherFactory {

    public static <T> Matcher<T> usingIgnoringFieldsComparator(final String... stringToIgnore) {
        return new Matcher<T>(stringToIgnore);
    }

    /**
     * @author Maxim Khamzin
     * @link <a href="https://mkcoder.net">mkcoder.net</a>
     */
    public static class Matcher<T> {

        private final String[] fieldsToIgnore;

        public Matcher(final String... fieldsToIgnore) {
            this.fieldsToIgnore = fieldsToIgnore;
        }

        public void assertMatch(final T actual, final T expected) {
            assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
        }

        @SuppressWarnings("unchecked")
        public void assertMatch(final Iterable<T> actual, final T... expected) {
            assertMatch(actual, Arrays.asList(expected));
        }

        public void assertMatch(final Iterable<T> actual, final Iterable<T> expected) {
            assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
        }
    }
}
