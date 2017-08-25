package com.example.rgukt.cooking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by rgukt on 3/25/2017.
 */
public class layout extends AppCompatActivity{
    String[] all={"Ashgourd","Artichoke","Aubergine","Eggplant","Brinjal","Banana-rawgreen","Beetroot","Bell pepper","Capsicum","Bitter Gourd","Bottle Gourd","Broad beans","Favabeans","Fieldbeans","Horsebeans","Broccoli","Brussels sprouts","Burtternut Squash","Cabbage","Capsicum","Bellpepper","Carrot","Cauliflower","Celeriac","Celery root","Celery","Chayot","Cilanto","Corianderleaves","Cluster beans","Collards","Corn-Maize","Cow peas","Cucumeber","Curryleaves","Dill-shepu-suva","Drumstic-moringa","Fennel - Shepu - Suva","Fenugreek leaves","Methi","Garlic","Green Beans-runner","Green chili","Green peas","Fress peas","Jackfruit-raw","Kales","Knolkol","LadyFinger-okra","Leeks","Lettuce","Madras onions","Mushroom","Mustard greens ","Onion","Parsnips","Potato","Pumpkin","Radish","Red Chard","Ridgegourd","Sponge-gourd","Rhubarb","Snake gourd","Spinach ","Sweet potato","Tamarind","Tapioca","Tomatoes","Turnips"};
    EditText et;
    String[] type={"Search By","Recipe Name","Available Ingrediants"};
    int types=1;
    String str="",pers="";
    Button btt;
    TextView t;
    GridView gv;
    ArrayList<fooditem> list;
    SQLiteDatabase sql;
    Spinner sp;
    itemadapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et=(EditText)findViewById(R.id.sear);
        t=(TextView)findViewById(R.id.sho);
        sp=(Spinner)findViewById(R.id.spi);
        btt=(Button)findViewById(R.id.button);
        sp.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type));
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        types=0;
                        btt.setEnabled(false);
                        btt.setBackgroundColor(Color.GRAY);
                        break;
                    case 2:
                        types=1;
                        btt.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AutoCompleteTextView at=(AutoCompleteTextView)findViewById(R.id.sear);
        at.setAdapter(new ArrayAdapter<String>(this,R.layout.textview,all));
        sql=openOrCreateDatabase("cook", Context.MODE_PRIVATE,null);
        gv=(GridView)findViewById(R.id.gv);
        list=new ArrayList<>();
        adapter = new itemadapter(this,R.layout.item,list);
        gv.setAdapter(adapter);
        Cursor c=sql.rawQuery("SELECT * FROM rcp",null);
        list.clear();
        while(c.moveToNext()){
            String name=c.getString(0);
            byte[] image=c.getBlob(4);
            list.add(new fooditem(name,image));
        }
        adapter.notifyDataSetChanged();
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),recipes.class);
                i.putExtra("id",list.get(position).getName());
                startActivity(i);
            }
        });
    }
    public void geting(View v) {
        str = et.getText().toString();
        if (str.equals(""))
            Toast.makeText(this, "please enter any ingredient", Toast.LENGTH_LONG).show();
        else {
            str=str+",";
            pers = pers + et.getText().toString() + "\n";
            t.setText(pers + "");
            et.setText("");
        }
    }
    public void search(View v){
        t.setText("");
        pers="";
        int count,t=0;
        Cursor c=sql.rawQuery("SELECT * FROM rcp",null);
        list.clear();
        if(types==0){
            while(c.moveToNext()){
                if(et.getText().toString().equalsIgnoreCase(c.getString(0))){
                    String name = c.getString(0);
                    byte[] image = c.getBlob(4);
                    list.add(new fooditem(name, image));
                }
            }
        }
        else if(types==1) {
            while (c.moveToNext()) {
                count = 0;
                StringTokenizer sr = new StringTokenizer(c.getString(3), ",");
                while (sr.hasMoreTokens()) {
                    String ra = sr.nextToken();
                    t = 0;
                    StringTokenizer st = new StringTokenizer(str, ",");
                    while (st.hasMoreTokens()) {
                        t++;
                        if (ra.equalsIgnoreCase(st.nextToken()))
                            count++;
                    }
                }
                if (((float) count / (float) t) * 100 >= 50) {
                    String name = c.getString(0);
                    byte[] image = c.getBlob(4);
                    list.add(new fooditem(name, image));
                }
            }
        }
        adapter.notifyDataSetChanged();
        str="";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inf=getMenuInflater();
        inf.inflate(R.menu.addmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.add:
                startActivity(new Intent(getApplicationContext(),creat.class));
                break;
        }
        return false;
    }
}
