package com.example.hehehe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText text;

    private SQLiteDatabase mDb;

    public void insertStudent(String fName, String fSurname) {
        String query = "INSERT INTO Students (name, surname) VALUES (\"" + fName + "\", \"" + fSurname + "\")";
        mDb.execSQL(query);
    }
    public void deleteStudent(View v) {
        String query = "DELETE FROM Students WHERE id = \"" + ((Button)v).getText().toString() + "\"";
        mDb.execSQL(query);
        updateList();
    }

    // Функция возвращает все значения таблицы студентов
    @NonNull
    private ArrayList<HashMap<String, Object>> returnStudents(){
        // Список студентов
        ArrayList<HashMap<String, Object>> clients = new ArrayList<>();

        // Список параметров конкретного студента
        HashMap<String, Object> client;

        // Отправляем запрос в БД
        Cursor cursor = mDb.rawQuery("SELECT * FROM Students", null);
        cursor.moveToFirst();

        // Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            client = new HashMap<>();

            // Заполняем студента
            client.put("id", cursor.getString(0));
            client.put("name",  cursor.getString(1));
            client.put("surname",  cursor.getString(2));

            // Закидываем студента в список клиентов
            clients.add(client);

            // Переходим к следующему студенту
            cursor.moveToNext();
        }
        cursor.close();
        return clients;
    }

    private void updateList() {
        SimpleAdapter adapter = new SimpleAdapter(this, returnStudents(), R.layout.adapter_item, new String[]{"id", "name", "surname"}, new int[]{R.id.button2, R.id.textView, R.id.textView2});
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);


        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        mDb = mDBHelper.getWritableDatabase();


        button.setOnClickListener(v -> {
            String[] str = text.getText().toString().split(" ");
            if (str.length == 2) {
                insertStudent(str[0], str[1]);
                updateList();
            }
        });
        updateList();
    }
}