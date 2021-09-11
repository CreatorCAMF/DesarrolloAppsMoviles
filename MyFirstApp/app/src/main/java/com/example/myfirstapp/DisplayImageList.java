package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DisplayImageList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static Cursor mCursor;
    public static FirebaseStorage FIRE_STORAGE;
    private static final String MY_FOLDER = "CAMF";
    private static PhotoView PHOTO_DISPLAYER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(myToolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 7);
        }
        else {
            getImages();
        }
        FIRE_STORAGE = FirebaseStorage.getInstance("gs://ndandroid-332aa.appspot.com");
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(layoutManager);

        PHOTO_DISPLAYER = (PhotoView)findViewById(R.id.photo_view_image_list);

        PHOTO_DISPLAYER.setVisibility(View.INVISIBLE);
        PHOTO_DISPLAYER.setBackgroundColor(Color.BLACK);
        // specify an adapter (see also next example)
        mAdapter = new MySelfiesAdapter(new ArrayList<imageItem>(),PHOTO_DISPLAYER);

        recyclerView.setAdapter(mAdapter);

        getImagesDataAsync();


    }

    @Override
    public void onBackPressed() {
        if(PHOTO_DISPLAYER.getVisibility()==View.VISIBLE)
            PHOTO_DISPLAYER.setVisibility(View.INVISIBLE);
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu_imagelist, menu);
        return true;
    }
    


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miUpload:
                Log.println(Log.ASSERT,"h","Picale al boton weee ");
                List<imageItem> filesToUpload = ((MySelfiesAdapter)mAdapter).getFilesToUpload();
                for(int x = 0; x < filesToUpload.size();x++)
                {
                    Log.println(Log.ASSERT,"h","ID:  "+ filesToUpload.get(x).getId());
                    uploadImages(filesToUpload.get(x).getData(),filesToUpload.get(x).getFileName());
                }
                MySelfiesAdapter.unSelectAll();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void displaySelectedImage(View view)
    {
        PhotoView pv = (PhotoView)findViewById(R.id.photo_view);
        Log.println(Log.ASSERT,"h", "Click "+pv.getVisibility());
        if(pv.getVisibility()==View.INVISIBLE)
        {

            pv.setVisibility(View.VISIBLE);
            pv.setImageURI(Uri.fromFile(new File(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)))));
        }
        else
        {
            pv.setVisibility(View.INVISIBLE);
        }
    }


    public void uploadImages(String image_data, final String file_name)
    {
        StorageReference storageRef = FIRE_STORAGE.getReference();
        Bitmap bitmap = BitmapFactory.decodeFile(image_data);
        StorageReference myImagesRef = storageRef.child(MY_FOLDER+"/"+file_name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = myImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.println(Log.ASSERT,"h","Error al cargar imagen "+file_name+ " en " + MY_FOLDER);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.println(Log.ASSERT,"h","Imagen"+file_name+" cargada exitosamente en " + MY_FOLDER);
            }
        });
    }



    public void getImages()
    {
        mCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);
        mCursor.moveToFirst();
    }

    public void getImagesDataAsync()
    {

        // An Async task always executes in new thread
        new Thread(new Runnable() {
            public void run()
            {

                mCursor.moveToFirst();
                while(!mCursor.isAfterLast())
                {
                    ((MySelfiesAdapter)mAdapter).addSelfie(new imageItem(
                            mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)),
                            mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media._ID)),
                            mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))));
                    mCursor.moveToNext();
                }
            }
        }).start();
    }


}


