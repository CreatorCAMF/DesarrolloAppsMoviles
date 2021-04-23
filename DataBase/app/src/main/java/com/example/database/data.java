package com.example.database;

public class data {
    private static final data ourInstance = new data();

    public static data getInstance() {
        return ourInstance;
    }

    private data() {
    }
}
