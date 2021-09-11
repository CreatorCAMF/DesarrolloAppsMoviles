package com.example.myfirstapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class MainActivity extends AppCompatActivity {

    public static Cursor mCursor;
    public static SharedPreferences KEY_DATA;
    public static FirebaseStorage FIRE_STORAGE;
    private static final String MY_FOLDER = "CAMF";
    private static final int SESION_GUARDADA = -1;
    private static PhotoView PHOTO_DISPLAYER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(myToolbar);

        KEY_DATA = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 7);
        }
        else {
            getImages();
        }
        FIRE_STORAGE = FirebaseStorage.getInstance("gs://ndandroid-332aa.appspot.com");
        //listCloudFiles();

    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = KEY_DATA.edit();
        editor.putInt(getString(R.string.CURSOR_POSITION), getCursosPosition());
        editor.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = KEY_DATA.edit();
        editor.putInt(getString(R.string.CURSOR_POSITION), getCursosPosition());
        editor.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(KEY_DATA.getInt(getString(R.string.CURSOR_POSITION),SESION_GUARDADA)!=SESION_GUARDADA)
            setCursorPosition(KEY_DATA.getInt(getString(R.string.CURSOR_POSITION),SESION_GUARDADA));
        else
            displayImage();
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(KEY_DATA.getInt(getString(R.string.CURSOR_POSITION),SESION_GUARDADA)+"");
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(KEY_DATA.getInt(getString(R.string.CURSOR_POSITION),SESION_GUARDADA)!=SESION_GUARDADA)
            setCursorPosition(KEY_DATA.getInt(getString(R.string.CURSOR_POSITION),SESION_GUARDADA));
        else
            displayImage();
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(KEY_DATA.getInt(getString(R.string.CURSOR_POSITION),SESION_GUARDADA)+"");

        PHOTO_DISPLAYER = (PhotoView)findViewById(R.id.photo_view);
        PHOTO_DISPLAYER.setBackgroundColor(Color.BLACK);
        PHOTO_DISPLAYER.setVisibility(View.INVISIBLE);


        /*pv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Log.println(Log.ASSERT,"h", "Click "+view.getVisibility());
                view.setVisibility(View.INVISIBLE);
                Log.println(Log.ASSERT,"h", "Click "+view.getVisibility());
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        if(PHOTO_DISPLAYER.getVisibility()==View.VISIBLE)
            PHOTO_DISPLAYER.setVisibility(View.INVISIBLE);
        else
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu_storage_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_archivos_cloud:
                goToFirebaseGalery();
                return true;

            case R.id.mi_archivos_locales:
                goToGalery();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void displaySelectedImage(View view)
    {
        if(PHOTO_DISPLAYER.getVisibility()==View.INVISIBLE)
        {
            PHOTO_DISPLAYER.setImageURI(Uri.fromFile(new File(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)))));
            PHOTO_DISPLAYER.setVisibility(View.VISIBLE);
        }
        else
        {
            PHOTO_DISPLAYER.setVisibility(View.INVISIBLE);
        }
    }



    public void listCloudFiles()
    {
        StorageReference listRef = FIRE_STORAGE.getReference();

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                            Log.println(Log.ASSERT,"h","Name " + prefix.getName());
                            Log.println(Log.ASSERT,"h","path " + prefix.getPath());
                            Log.println(Log.ASSERT,"h","URL " + prefix.getDownloadUrl());
                            StorageReference listRef2 = FIRE_STORAGE.getReference().child(prefix.getPath());
                            listRef2.listAll()
                                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                        @Override
                                        public void onSuccess(ListResult listResult) {
                                            for (StorageReference prefix : listResult.getPrefixes()) {
                                                // All the prefixes under listRef.
                                                // You may call listAll() recursively on them.
                                                Log.println(Log.ASSERT,"h","Name " + prefix.getName());
                                                Log.println(Log.ASSERT,"h","path " + prefix.getPath());
                                                Log.println(Log.ASSERT,"h","URL " + prefix.getDownloadUrl());
                                                StorageReference listRef2 = FIRE_STORAGE.getReference().child(prefix.getPath());

                                            }

                                            for (StorageReference item : listResult.getItems()) {
                                                // All the items under listRef.
                                                Log.println(Log.ASSERT,"h","Name " + item.getName());
                                                Log.println(Log.ASSERT,"h","path " + item.getPath());
                                                item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        Log.println(Log.ASSERT,"h","Item URL " + uri.toString());
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception exception) {
                                                        // Handle any errors
                                                    }
                                                });

                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Uh-oh, an error occurred!
                                        }
                                    });

                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            Log.println(Log.ASSERT,"h","Name " + item.getName());
                            Log.println(Log.ASSERT,"h","path " + item.getPath());
                            Log.println(Log.ASSERT,"h","URL " + item.getDownloadUrl());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });
    }


    public void uploadSelfie(View view)
    {
        StorageReference storageRef = FIRE_STORAGE.getReference();
        String file_name=mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
        StorageReference myImagesRef = storageRef.child(MY_FOLDER+"/"+file_name);

        ImageView iv = (ImageView)findViewById(R.id.ivLoad);
        iv.setImageURI(Uri.fromFile(new File(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)))));
        // Get the data from an ImageView as bytes
        iv.setDrawingCacheEnabled(true);
        iv.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = myImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.println(Log.ASSERT,"h","Error al cargar la imagen en " + MY_FOLDER);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.println(Log.ASSERT,"h","Imagen  cargada exitosamente en " + MY_FOLDER);
            }
        });
    }



    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }

    public void goToGalery() {
        Intent intent = new Intent(this, DisplayImageList.class);
        startActivity(intent);
    }

    public void goToFirebaseGalery() {
        Intent intent = new Intent(this, display_firebase_files.class);
        startActivity(intent);
    }


    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        if(!mCursor.isLast())
            mCursor.moveToNext();
        else
            mCursor.moveToFirst();
        displayImage();
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media._ID))+"");
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
    public int getCursosPosition()
    {
        return mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media._ID));
    }
    public void setCursorPosition(int pos)
    {
        mCursor.moveToFirst();
        while(!mCursor.isLast())
        {
            if(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media._ID))==pos)
                break;
            mCursor.moveToNext();
        }
        displayImage();

    }

    public void displayImage()
    {
        ImageView iv = (ImageView)findViewById(R.id.ivLoad);
        Glide.with(this)
                .load(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)))
                .thumbnail(0.1f)
                .into(iv);


        //######Forma de hacerlo sin Glide #####//
        /*Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeFile(
                        mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA))),180,180);
        iv.setImageBitmap(ThumbImage);*/

        //iv.setImageURI(Uri.fromFile(new File(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)))));
    }













    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 7: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getImages();
                }
                else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }







}


