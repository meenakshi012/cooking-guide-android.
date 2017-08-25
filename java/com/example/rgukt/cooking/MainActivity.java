package com.example.rgukt.cooking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sql;
    EditText un,pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        un=(EditText)findViewById(R.id.user);
        pd=(EditText)findViewById(R.id.pwd);
        sql=openOrCreateDatabase("cook", Context.MODE_PRIVATE,null);
        sql.execSQL("create table if not exists usr(name varchar(255),email varchar(255),phone int,password varchar(255))");

    }
    public void login(View v) {
        Cursor c = sql.query("usr", new String[]{"name", "password"}, "name=?", new String[]{un.getText().toString()}, null, null, null);
        while (c.moveToNext()) {
            if (c.getString(0).equalsIgnoreCase(un.getText().toString())) {
                if (c.getString(1).equals(pd.getText().toString())) {
                    startActivity(new Intent(this,layout.class));
                } else

                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Invalid UserName", Toast.LENGTH_SHORT).show();
        }
    }
    public void rf(View v){
        Intent i=new Intent(this,reg.class);
        startActivity(i);
        finish();
    }
    public void about(View v)
    {
        Intent j=new Intent(this,about.class);
        startActivity(j);
        finish();
    }
    public void forgot(View v)
    {
        Intent p=new Intent(this,forgot.class);
        startActivity(p);
        finish();
    }

}
