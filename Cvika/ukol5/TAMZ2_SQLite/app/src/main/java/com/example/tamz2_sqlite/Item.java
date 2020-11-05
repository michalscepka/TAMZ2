package com.example.tamz2_sqlite;

public class Item {

    public int id;
    public String name;
    public int cost;

    public Item(int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name + " - " + cost + " Kč";
    }
}
