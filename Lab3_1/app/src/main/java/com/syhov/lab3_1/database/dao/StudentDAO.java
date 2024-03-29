package com.syhov.lab3_1.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.syhov.lab3_1.Student;

import java.sql.SQLException;
import java.util.Date;

public class StudentDAO extends BaseDaoImpl<Student, Integer> {
    private static final String[] names = {"Michael", "Ivan", "Vladimir", "Sergey"};
    private static final String[] lastNames = {"Krukov", "Petrov", "Ivanov", "Menyailov"};
    private static final String[] middleNames = {"Sergeevich", "Ivanovich"};

    public StudentDAO(ConnectionSource connectionSource,
                      Class<Student> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    private static <T> T getRandom(T[] array) {
        java.util.Random random = new java.util.Random();

        int random_computer_card = random.nextInt(array.length);

        return array[random_computer_card];
    }

    public Student generateStudent() {
        Student student = new Student();

        student.setAdded(new Date());
        student.setFullname(getRandom(names) + " " + getRandom(lastNames) + " " + getRandom(middleNames));

        return student;
    }
}