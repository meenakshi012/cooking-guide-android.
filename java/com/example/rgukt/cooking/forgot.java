package com.example.rgukt.cooking;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class forgot extends AppCompatActivity {
    EditText email, phone, password;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.ph);
        password = (EditText) findViewById(R.id.password);
        sql = openOrCreateDatabase("cook", Context.MODE_PRIVATE, null);
    }

    public void submit(View v) {
        Cursor c = sql.rawQuery("SELECT * FROM usr", null);
        ContentValues n = new ContentValues();
        while (c.moveToNext()) {
            if (c.getString(1).equals(email.getText().toString())) {
                if (c.getString(2).equals(phone.getText().toString())) {
                    if (password.getText().toString().length() > 8) {
                        n.put("password", password.getText().toString());
                        sql.update("usr", n, "email=?", new String[]{email.getText().toString()});
                        Toast.makeText(this, "successfully uploaded", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        Toast.makeText(this, "password should be greater than 8 characters", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, "phone number is not valid", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "email is not valid", Toast.LENGTH_LONG).show();
            }
        }
    }
}



