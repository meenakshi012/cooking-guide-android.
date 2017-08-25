package com.example.rgukt.cooking;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by rgukt on 3/25/2017.
 */
public class reg extends Activity{
    EditText nm,em,ph,psw;
    SQLiteDatabase sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        nm=(EditText)findViewById(R.id.name);
        em=(EditText)findViewById(R.id.mail);
        ph=(EditText)findViewById(R.id.ph);
        psw=(EditText)findViewById(R.id.paswd);
        sql=openOrCreateDatabase("cook", Context.MODE_PRIVATE,null);
        sql.execSQL("create table if not exists rcp(rname varchar(255),disc TEXT,way TEXT,ing TEXT,pic BLOB)");
    }
    public void subm(View v){
        Cursor c=sql.rawQuery("SELECT * FROM usr",null);
        int tr=0;
        while(c.moveToNext()) {
            if (c.getString(0).equals(nm.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Sorry User name already exists", Toast.LENGTH_SHORT).show();
                nm.setText("");
                tr = 1;
                break;
            }
            else {
                if (c.getString(1).equals(em.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Sorry Email already exists", Toast.LENGTH_SHORT).show();
                    em.setText("");
                    tr = 1;
                    break;
                }
            }
        }
            if (tr == 0) {
                int p = 0, m = 0, phn = 0, pswdd = 0;
                ContentValues n = new ContentValues();
                if (nm.getText().length() == 0) {
                    nm.setError("Plz fill this");
                    p = 0;
                } else {
                    n.put("name", nm.getText().toString());
                    p = 1;
                }
                if (em.getText().length() == 0) {
                    em.setError("Plz fill this");
                    m = 0;
                } else {
                    n.put("email", em.getText().toString());
                    m = 1;
                }
                if (ph.getText().length() == 0 || ph.getText().length() < 10 || ph.getText().length() > 10) {
                    ph.setError("Plz fill this");
                    phn = 0;
                } else {
                    n.put("phone", ph.getText().toString());
                    phn = 1;
                }
                if (psw.getText().length() == 0 || psw.getText().length() < 8) {
                    psw.setError("password must be grater than 8 charcters");

                    pswdd = 0;
                } else {
                    n.put("password", psw.getText().toString());
                    pswdd = 1;
                }
                if (p == 1 && m == 1 && phn == 1 && pswdd == 1) {
                    sql.insert("usr", null, n);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }
}
