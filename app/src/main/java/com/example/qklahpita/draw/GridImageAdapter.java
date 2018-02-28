package com.example.qklahpita.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends BaseAdapter {
    List<ImageModel> imageModelList = ImageUtils.getListImage();
    Context context;

    public GridImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Create ImageView
        ImageView imageView = new ImageView(context);

        imageView.setLayoutParams(
                new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(16,16,16,16);
        imageView.setCropToPadding(true);

        //Set Image
        Bitmap bitmap = BitmapFactory.decodeFile(imageModelList.get(i).path);
        imageView.setImageBitmap(bitmap);

        imageView.setTag(i);

        return imageView;
    }
}
