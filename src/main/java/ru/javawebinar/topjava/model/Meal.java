package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {

    private final Integer id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    // Конструктор для объектов с заданным id
    public Meal(final Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    // Конструктор для новых объектов (id будет установлен позднее)
    public Meal(final LocalDateTime dateTime, final String description, final int calories) {
        this(null, dateTime, description, calories);
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
