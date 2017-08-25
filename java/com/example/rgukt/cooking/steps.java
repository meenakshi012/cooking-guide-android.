package com.example.rgukt.cooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.StringTokenizer;

/**
 * Created by rgukt on 4/1/2017.
 */
public class steps extends Activity {
    TextView t;
    SQLiteDatabase sql;
    String s="",str="";
    StringTokenizer st=new StringTokenizer(str,".");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook);
        t=(TextView)findViewById(R.id.st);
        sql=openOrCreateDatabase("cook", Context.MODE_PRIVATE,null);
        Intent i=getIntent();
        s=i.getStringExtra("id");
        st=new StringTokenizer(s,".");
    }
    public void next(View v){
        if(st.hasMoreTokens()) {
            t.setText(st.nextToken());
        }
        else
            Toast.makeText(getApplicationContext(),"Complete",Toast.LENGTH_SHORT).show();
    }
}
