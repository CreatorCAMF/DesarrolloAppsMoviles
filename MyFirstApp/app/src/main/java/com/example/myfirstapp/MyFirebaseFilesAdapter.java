package com.example.myfirstapp;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class MyFirebaseFilesAdapter extends RecyclerView.Adapter<MyFirebaseFilesAdapter.MyFirebaseFilesViewHolder> {
    public static List<firebaseItem> FIREBASE_FILE_DATA;
    public static FirebaseStorage FIRE_STORAGE;
    public static PhotoView PHOTO_DISPLAYER;

    public static class MyFirebaseFilesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView iv;
        public MyFirebaseFilesViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv_selfie);
        }
    }

    public MyFirebaseFilesAdapter(List<firebaseItem> firebaseFilesData, FirebaseStorage fire_storage, PhotoView photo_displayer)
    {
        this.FIREBASE_FILE_DATA = firebaseFilesData;
        this.FIRE_STORAGE = fire_storage;
        this.PHOTO_DISPLAYER = photo_displayer;
        Log.println(Log.ASSERT,"h","Data " + FIREBASE_FILE_DATA.size());
    }

    @Override
    public MyFirebaseFilesAdapter.MyFirebaseFilesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View iv = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_layout, parent, false);

        MyFirebaseFilesAdapter.MyFirebaseFilesViewHolder vh = new MyFirebaseFilesAdapter.MyFirebaseFilesViewHolder(iv);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyFirebaseFilesAdapter.MyFirebaseFilesViewHolder holder, final int position) {
        final  firebaseItem fbItem = FIREBASE_FILE_DATA.get(position);
        Glide.with(holder.iv.getContext())
                .load(fbItem.getURL())
                .thumbnail(0.01f)
                .into(holder.iv);
        holder.iv.setBackgroundColor(fbItem.getSelected() ? Color.CYAN : Color.WHITE);
        holder.iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                fbItem.setSelected(!fbItem.getSelected());
                holder.iv.setImageTintList(
                        fbItem.getSelected()?
                                ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),R.color.colorSelect)):
                                ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),android.R.color.transparent)));
                Log.println(Log.ASSERT,"h","Imgane seleccionada " + fbItem.getURL());
                return true;
            }
        });
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(holder.iv.getContext())
                        .load(fbItem.getURL())
                        //.thumbnail(1f)
                        .into(PHOTO_DISPLAYER);
                PHOTO_DISPLAYER.setVisibility(View.VISIBLE);
            }
        });
    }
    public int getItemCount() {
        return FIREBASE_FILE_DATA.size();
    }

    public void addSelfie(firebaseItem file)
    {
        FIREBASE_FILE_DATA.add(file);
    }
}
