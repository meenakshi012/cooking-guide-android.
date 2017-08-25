package com.example.rgukt.cooking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rgukt on 3/25/2017.
 */
public class itemadapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<fooditem> itemlist;

    public itemadapter(Context context, int layout, ArrayList<fooditem> itemlist) {
        this.context = context;
        this.layout = layout;
        this.itemlist = itemlist;
    }
    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return itemlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtnm;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row= view;
        ViewHolder holder=new ViewHolder();
        if(row==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.txtnm=(TextView)row.findViewById(R.id.txtname);
            holder.imageView=(ImageView)row.findViewById(R.id.imagev);
            row.setTag(holder);
        }
        else{
            holder=(ViewHolder)row.getTag();
        }
        fooditem fi=itemlist.get(position);
        holder.txtnm.setText(fi.getName());
        byte[] fimage=fi.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(fimage,0,fimage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
