package com.example.myfirstapp;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;


public class MySelfiesAdapter extends RecyclerView.Adapter<MySelfiesAdapter.MySelfieViewHolder>{
    public static List<imageItem> IMAGE_DATA;
    public static PhotoView PHOTO_DISPLAYER;

    public static class MySelfieViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView iv;
        public MySelfieViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv_selfie);
        }
    }

    public MySelfiesAdapter(List<imageItem> imagesData, PhotoView photo_displayer)
    {
        PHOTO_DISPLAYER = photo_displayer;
        IMAGE_DATA = imagesData;
    }

    @Override
    public MySelfiesAdapter.MySelfieViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View iv = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_layout, parent, false);

        MySelfieViewHolder vh = new MySelfieViewHolder(iv);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MySelfieViewHolder holder, int position) {
        final imageItem imgItem = IMAGE_DATA.get(position);
        Glide.with(holder.iv.getContext())
                .load(imgItem.getData())
                .thumbnail(0.01f)
                .into(holder.iv);
        holder.iv.setBackgroundColor(imgItem.getSelected() ? Color.CYAN : Color.WHITE);
        holder.iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                imgItem.setSelected(!imgItem.getSelected());
                holder.iv.setImageTintList(
                        imgItem.getSelected()?
                                ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),R.color.colorSelect)):
                                ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),android.R.color.transparent)));
                Log.println(Log.ASSERT,"h","Imgane seleccionada " + imgItem.getId());
                return true;
            }
        });
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(holder.iv.getContext())
                        .load(imgItem.getData())
                        //.thumbnail(1f)
                        .into(PHOTO_DISPLAYER);
                PHOTO_DISPLAYER.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return IMAGE_DATA.size();
    }

    public List<imageItem> getFilesToUpload()
    {
        List<imageItem> FilesToUpload = new ArrayList<imageItem>();
        for(int x=0; x<IMAGE_DATA.size();x++)
        {
            if(IMAGE_DATA.get(x).getSelected())
                FilesToUpload.add(IMAGE_DATA.get(x));
        }
        return FilesToUpload;
    }

    public static void unSelectAll()
    {
        for(int x=0; x<IMAGE_DATA.size();x++)
        {
                IMAGE_DATA.get(x).setSelected(false);
        }
    }

    public void addSelfie(imageItem selfie)
    {
        IMAGE_DATA.add(selfie);
    }

    public void dispalyImagAsync(final imageItem imgItem, final MySelfieViewHolder holder)
    {

        // An Async task always executes in new thread
        new Thread(new Runnable() {
            public void run()
            {

                Glide.with(holder.iv.getContext())
                        .load(imgItem.getData())
                        .thumbnail(0.1f)
                        .into(holder.iv);
            }
        }).start();
    }
}

