package com.yaypay.challenge.entities;

import java.time.LocalDate;
import java.util.Map;

public class DebugDude {
    int age;
    String name;
    LocalDate date;
    Double money;
    boolean gay;
    private Map<String, String> stuff;

    public DebugDude(int age, String name, LocalDate date, Double money, boolean gay, Map<String, String> stuff) {
        this.age = age;
        this.name = name;
        this.date = date;
        this.money = money;
        this.gay = gay;
        this.stuff = stuff;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getMoney() {
        return money;
    }

    public boolean isGay() {
        return gay;
    }

    public Map<String, String> getStuff() {
        return stuff;
    }
}
