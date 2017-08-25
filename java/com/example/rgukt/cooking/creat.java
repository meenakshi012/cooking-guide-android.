package com.example.rgukt.cooking;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by rgukt on 3/25/2017.
 */
public class creat extends AppCompatActivity {
    String[] all={"Ashgourd","Artichoke","Aubergine","Eggplant","Brinjal","Banana-rawgreen","Beetroot","Bell pepper","Capsicum","Bitter Gourd","Bottle Gourd","Broad beans","Favabeans","Fieldbeans","Horsebeans","Broccoli","Brussels sprouts","Burtternut Squash","Cabbage","Capsicum","Bellpepper","Carrot","Cauliflower","Celeriac","Celery root","Celery","Chayot","Cilanto","Corianderleaves","Cluster beans","Collards","Corn-Maize","Cow peas","Cucumeber","Curryleaves","Dill-shepu-suva","Drumstic-moringa","Fennel - Shepu - Suva","Fenugreek leaves","Methi","Garlic","Green Beans-runner","Green chili","Green peas","Fress peas","Jackfruit-raw","Kales","Knolkol","LadyFinger-okra","Leeks","Lettuce","Madras onions","Mushroom","Mustard greens ","Onion","Parsnips","Potato","Pumpkin","Radish","Red Chard","Ridgegourd","Sponge-gourd","Rhubarb","Snake gourd","Spinach ","Sweet potato","Tamarind","Tapioca","Tomatoes","Turnips"};
    EditText nam,disc,pro,et;
    String str="",pers="",nstr="";
    TextView t;
    ImageView image;
    AutoCompleteTextView at;
    final int REQUEST_CODE_GALLERY=999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);
        et=(EditText) findViewById(R.id.sear);
        image=(ImageView)findViewById(R.id.imag);
        nam=(EditText)findViewById(R.id.rnm);
        disc=(EditText)findViewById(R.id.di);
        pro=(EditText)findViewById(R.id.pr);
        t=(TextView)findViewById(R.id.sho);
        at=(AutoCompleteTextView)findViewById(R.id.sear);
        at.setAdapter(new ArrayAdapter<String>(this,R.layout.textview,all));
    }
    public void choose(View v){
        ActivityCompat.requestPermissions(
                creat.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
        );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);

            }
            else{
                Toast.makeText(this,"no permission",Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK&&data!=null)
        {
            Uri uri=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
            }catch (FileNotFoundException e)
            {
                Toast.makeText(this,"filenotfound", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void geting(View v) {
        if (et.getText().toString() == "") {
            Toast.makeText(this, "please enter any ingredient", Toast.LENGTH_LONG).show();
        } else {
            str = str + et.getText().toString() + ",";
            pers = pers + et.getText().toString() + "\n";
            t.setText(pers + "");
            et.setText("");
        }
    }
    public void submit(View v){
        t.setText("");
        pers="";
        int tr=0;
        SQLiteDatabase sql = openOrCreateDatabase("cook", Context.MODE_PRIVATE, null);
        Cursor c=sql.rawQuery("SELECT * FROM rcp",null);
        while(c.moveToNext()){
            if(c.getString(0).equals(nam.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Sorry Recipe name already exists", Toast.LENGTH_SHORT).show();
                tr=1;
                break;
            }
        }
        if(tr==0){
            ContentValues cv = new ContentValues();
            try {
                cv.put("rname", nam.getText().toString());
                cv.put("disc", disc.getText().toString());
                cv.put("way", pro.getText().toString());
                cv.put("ing", str);
                cv.put("pic", imageToByte(image));

            } catch (Exception aee) {
                Toast.makeText(this, ""+aee, Toast.LENGTH_SHORT).show();
            }
            sql.insert("rcp", null, cv);
            Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), layout.class));
            str="";
            finish();
        }
    }
    private byte[] imageToByte(ImageView image) {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray= stream.toByteArray();
        return byteArray;
    }
}