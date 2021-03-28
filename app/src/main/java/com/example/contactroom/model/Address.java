package com.example.contactroom.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "address_table")
public class Address {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String address;

    public Address(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
