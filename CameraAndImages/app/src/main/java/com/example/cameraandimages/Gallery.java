package com.example.cameraandimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Gallery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        List<Selfie> selfiesGallery = new ArrayList<Selfie>();

        String[] projection = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
        };

        try{
            Cursor cursor = getApplicationContext().getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null
            );



            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME );

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                selfiesGallery.add(new Selfie(contentUri, name));
            }
            cursor.close();
        }catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR en cursor",e.toString());
        }


        try{
            recyclerView = (RecyclerView) findViewById(R.id.rvData);

            recyclerView.setHasFixedSize(false);


            layoutManager = new GridLayoutManager(this,4,RecyclerView.VERTICAL,false );

            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new selfieAdapter(selfiesGallery, getApplicationContext());

            recyclerView.setAdapter(mAdapter);
        }
        catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR en cursor",e.toString());
        }



    }

}


class Selfie{
    public Uri uri;
    public String name;

    public Selfie(Uri uri, String name) {
        this.uri = uri;
        this.name = name;
    }
}


class selfieAdapter extends RecyclerView.Adapter<selfieAdapter.selfieViewHolder>{

    public static List<Selfie> SELFIES;
    private  static  Context CONTEXT;


    public selfieAdapter(List<Selfie> selfies, Context context)
    {
        SELFIES = selfies;
        CONTEXT = context;

    }

    public static class selfieViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imgSelfie;
        public selfieViewHolder(View v) {
            super(v);
            imgSelfie = (ImageView) v.findViewById(R.id.imgSelfie);
        }
    }


    @Override
    public selfieAdapter.selfieViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View selfieItem = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false);

        selfieViewHolder vh = new selfieViewHolder(selfieItem);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final selfieViewHolder holder, int position) {
        final Selfie selfieItem = SELFIES.get(position);
        try {
            Bitmap thumbnail = CONTEXT.getContentResolver().loadThumbnail(selfieItem.uri, new Size(640, 480), null);
            holder.imgSelfie.setImageBitmap(thumbnail);
        } catch (IOException e) {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

    }

    @Override
    public int getItemCount()
    {
        return SELFIES.size();
    }

}