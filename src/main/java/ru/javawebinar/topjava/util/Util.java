package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public final class Util {

    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(final T value, @Nullable final T start, @Nullable final T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

}
