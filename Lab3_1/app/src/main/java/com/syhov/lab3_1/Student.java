package com.syhov.lab3_1;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "students")
public class Student {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String fullname;

    @DatabaseField(canBeNull = false)
    private Date added;

    public Student() {};

    public int getId() {
        return id;
    }

    public Date getAdded() {
        return added;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAdded(Date added) {
        this.added = added;
    }
}
