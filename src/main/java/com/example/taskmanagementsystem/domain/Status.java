package com.example.taskmanagementsystem.domain;

public enum Status {
    //    PENDING, PROGRESS, COMPLETED;
    PENDING("В ожидании"), PROGRESS("В процессе"), COMPLETED("Завершено");
    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
