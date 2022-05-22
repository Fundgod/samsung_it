package com.example.hehehe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    // Потом будет ArrayList<String>
    // Функция возвращает все значения таблицы студентов
    private String returnStudents(){
        String product = "";
        Cursor cursor = mDb.rawQuery("SELECT * FROM Students", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getString(1) + " | ";
            cursor.moveToNext();
        }
        cursor.close();
        return product;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Тут с БД работа
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        mDb = mDBHelper.getWritableDatabase();
        // Конец работы

        setContentView(R.layout.activity_main);
    }
}