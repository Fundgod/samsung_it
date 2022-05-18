package com.example.hehehe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private ArrayList<String> returnStudents(SQLiteDatabase db, int Course_id){
        final String DB_NAME = "Haha";
        SQLiteDatabase dbd = getBaseContext().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        return new ArrayList<String>();


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