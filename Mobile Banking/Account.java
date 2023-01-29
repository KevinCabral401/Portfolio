package com.example.mobilebanking;

import java.util.ArrayList;

public class Account {
    private String number;
    private String name;
    private double amount;
    private String type;
    public static ArrayList<String> recentActivity = new ArrayList<>();

    public Account(String number, String name, double amount, String type){
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    static{

    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}

