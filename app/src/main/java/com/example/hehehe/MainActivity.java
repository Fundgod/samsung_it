package com.example.hehehe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;


    public void insertStudent(String name, String surname, String patronymic, String birthday, String grou) {
        String query = "INSERT INTO students (name, surname, patronymic, birthday, group) VALUES (" + name + ", " + surname + ", " + patronymic + ", " + birthday + ", " + grou +")";
        System.out.println(query);
        mDb.execSQL(query);
    }

    // Функция возвращает все значения таблицы студентов
    private ArrayList<HashMap<String, Object>> returnStudents(){
        // Список клиентов
        ArrayList<HashMap<String, Object>> clients = new ArrayList<>();

        // Список параметров конкретного клиента
        HashMap<String, Object> client;

        // Отправляем запрос в БД
        Cursor cursor = mDb.rawQuery("SELECT * FROM Students", null);
        cursor.moveToFirst();

        // Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            client = new HashMap<>();

            // Заполняем клиента
            client.put("id", cursor.getInt(0));
            client.put("name",  cursor.getString(1));
            client.put("surname",  cursor.getString(2));
            client.put("patronymic", cursor.getString(3));
            client.put("birthday", cursor.getString(4));
            client.put("group", cursor.getString(5));
            // Закидываем клиента в список клиентов
            clients.add(client);

            // Переходим к следующему клиенту
            cursor.moveToNext();
        }
        cursor.close();
        return clients;

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

        //setContentView(R.layout.activity_main);

        System.out.println(returnStudents());
        System.out.println("11111");
    }
}