package com.example.cameraandimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.cameraandimages.App.context;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(isStoragePermissionGranted())
        {
            Log.println(Log.ASSERT,"OK","Permisos necesarios");
        }
    }

    public boolean isStoragePermissionGranted() {
        final int STORAGE_PERMISSION = 100;
        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final int STORAGE_PERMISSION = 100;
        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.println(Log.ASSERT,"OK","Permisos obtenidos");
        }
        else
        {
            Log.println(Log.ASSERT,"NOK","No pos fue GG ya ni que hacerle");
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void butFirstLetMeTakeASelfie(View view)
    {
        try
        {
            int REQUEST_TAKE_PHOTO = 1;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView selfie = (ImageView)findViewById(R.id.imgSelfie);
            selfie.setImageBitmap(imageBitmap);



            String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String name = "IMG_" + dateTime + "_";
            saveMe(name,imageBitmap);
        }
    }

    private void saveMe(String nombre, Bitmap img)
    {
        try{
            //final String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Your Directory"; // save directory
            String fileName = nombre;
            //String mimeType = "image/*"; // Mime Types define here
            Bitmap bitmap = img;

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            //contentValues.put(MediaStore.MediaColumns.TITLE, "Pantalones");
            //contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            //contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath);

            ContentResolver resolver = getApplicationContext().getContentResolver();

            OutputStream stream = null;
            Uri uri = null;

            try {
                final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                uri = resolver.insert(contentUri, contentValues);

                stream = resolver.openOutputStream(uri);

                boolean saved = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);

            } catch (IOException e) {
                if (uri != null) {
                    resolver.delete(uri, null, null);
                }

            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            Log.println(Log.ASSERT,"ERROR en cursor",e.toString());
        }

    }


    public void next(View view)
    {
        Intent intent = new Intent(this, Gallery.class);
        startActivity(intent);
    }

}
