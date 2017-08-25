package com.example.rgukt.cooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rgukt on 3/25/2017.
 */
public class recipes extends AppCompatActivity {
    TextView a,b,d,j;
    SQLiteDatabase sql;
    ImageView im;
    String s="",str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        a=(TextView)findViewById(R.id.name);
        b=(TextView)findViewById(R.id.disc);
        d=(TextView)findViewById(R.id.proc);
        j=(TextView) findViewById(R.id.ing);
        im=(ImageView)findViewById(R.id.imagee);
        Intent i=getIntent();
        String s=i.getStringExtra("id");
        sql=openOrCreateDatabase("cook", Context.MODE_PRIVATE,null);
        Cursor c=sql.rawQuery("SELECT * FROM rcp",null);
        while(c.moveToNext()) {
            if(c.getString(0).equals(s)){
                a.setText(c.getString(0));
                b.setText(c.getString(1));
                str=c.getString(2);
                d.setText(str);
                j.setText(c.getString(3));
                byte[] fimage=c.getBlob(4);
                Bitmap bitmap= BitmapFactory.decodeByteArray(fimage,0,fimage.length);
                im.setImageBitmap(bitmap);
            }
        }
    }
    public void step(View v){
        Intent j=new Intent(getApplicationContext(),steps.class);
        j.putExtra("id",str);
        startActivity(j);

    }
}
