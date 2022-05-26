package com.example.hehehe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button2;
    EditText text;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public void insertStudent(String fName, String fSurname) {
        String query = "INSERT INTO Students (name, surname) VALUES (\"" + fName + "\", \"" + fSurname + "\")";
        mDb.execSQL(query);
    }

    public void deleteStudent(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        // System.out.println(b.getHint() + " 22");
        // System.out.println(" 322");
        String query = "DELETE FROM Students WHERE id = \"" + buttonText + "\"";
        mDb.execSQL(query);
        updateList();
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
            client.put("id", cursor.getString(0));
            client.put("name",  cursor.getString(1));
            client.put("surname",  cursor.getString(2));

            // Закидываем клиента в список клиентов
            clients.add(client);

            // Переходим к следующему клиенту
            cursor.moveToNext();
        }
        cursor.close();
        return clients;
    }

    private void updateList() {
        SimpleAdapter adapter = new SimpleAdapter(this, returnStudents(), R.layout.adapter_item, new String[]{"name", "surname", "id"}, new int[]{R.id.textView, R.id.textView2, R.id.button2});
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        mDb = mDBHelper.getWritableDatabase();
        System.out.println("YES");


        text = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);


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