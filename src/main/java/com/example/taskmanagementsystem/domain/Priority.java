package com.example.taskmanagementsystem.domain;

public enum Priority {
    //    HIGH, MID, LOW;
    HIGH("Высокий"), MID("Средний"), LOW("Низкий");
    private final String name;

    Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
