package com.example.holamundo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class camara extends AppCompatActivity {

    private Bitmap IMGBMP = null;

    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        if(tengoPermisos())
        {
            Log.println(Log.ASSERT, "OK", "Permisos ok");
        }


    }

    public boolean tengoPermisos()
    {
        final int STORAGE_PERMISSION = 100;
        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if((ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int [] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        final int STORAGE_PERMISSION = 100;
        if(requestCode == STORAGE_PERMISSION &&grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Log.println(Log.ASSERT, "OK", "Permisos otrogados");
        }else
        {
            Log.println(Log.ASSERT, "OK", "Permisos NO otrogados");
        }
    }



    static final int REQUEST_IMAGE_CAPTURE=1;

    public void letMeTakeASelfie(View view)
    {
        Intent fotito = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(fotito.resolveActivity(getPackageManager())!=null){
            startActivityForResult(fotito,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.println(Log.ASSERT, "OK", "Llegamos a ActivityResult");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle infoExtra = data.getExtras();
            IMGBMP = (Bitmap) infoExtra.get("data");
            ImageView imgSelfie = (ImageView) findViewById(R.id.imgSelfie);
            imgSelfie.setImageBitmap(IMGBMP);
            Log.println(Log.ASSERT, "OK", "Pinta imagen");
        }
    }

    public void saveSalfie(View view)
    {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String  name ="IMG"+fecha;
        ContentValues metad= new ContentValues();
        metad.put(MediaStore.MediaColumns.DISPLAY_NAME, name);

        ContentResolver solucionador =getApplicationContext().getContentResolver();

        OutputStream stream = null;

        Uri uri = null;

        final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        uri = solucionador.insert(contentUri,metad);

        try {
            stream = solucionador.openOutputStream(uri);
            boolean saved = IMGBMP.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(stream!=null)
            {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void searchSelfie(View view){
        List<fotito> fotitos = new ArrayList<fotito>();
        String[] revelado = new String[]{
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        };
        Cursor cursor =getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                revelado,
                null,
                null,
                null);

        int columnaId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int colmnaName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

        while (cursor.moveToNext())
        {
            long id =cursor.getLong(columnaId);
            String name = cursor.getString(colmnaName);
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id);

            fotito foti = new fotito(uri,name);
            fotitos.add(foti);
            Log.println(Log.ASSERT,"OK", foti.toString());
        }

    }

    public void Listar(View view)
    {
        StorageReference listaArchivos = storage.getReference();
        listaArchivos.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for(StorageReference prefijo : listResult.getPrefixes()){
                            Log.println(Log.ASSERT,"DIRECTORIO", prefijo.toString());
                        }
                        for(StorageReference item: listResult.getItems()){
                            Log.println(Log.ASSERT,"ARCHIVO", item.toString());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.println(Log.ASSERT,"NOK", e.toString());
                    }
                });
    }

    public void Cargar(View view)
    {
        StorageReference listaArchivos = storage.getReference();
        StorageReference imgCarga = listaArchivos.child("miImagenCargada.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IMGBMP.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] data = baos.toByteArray();
        UploadTask carga = imgCarga.putBytes(data);
        carga.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.println(Log.ASSERT,"OK", "Cargado ...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.println(Log.ASSERT,"NOK", e.toString());
            }
        });
    }

    public void Descargar(View view)
    {
        StorageReference listaArchivos = storage.getReference();
        StorageReference imagenDescarga = listaArchivos.child("a.png");
        long MEGA = 1024*1024;
        imagenDescarga.getBytes(MEGA).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitsImagenDescargada = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ImageView imgSelfie = (ImageView) findViewById(R.id.imgSelfie);
                imgSelfie.setImageBitmap(bitsImagenDescargada);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.println(Log.ASSERT,"NOK", e.toString());
            }
        });
    }

    public void Eliminar(View view){
        StorageReference listaArchivos = storage.getReference();
        StorageReference imgSaraConnor = listaArchivos.child("miImagenCargada.png");

        imgSaraConnor.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.println(Log.ASSERT,"TERMINATOR", "Hasta la vista baby");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.println(Log.ASSERT,"NOK", e.toString());
            }
        });
    }




}

class fotito{
    public Uri uri;
    public String name;

    public fotito(Uri uri,String name)
    {
        this.uri = uri;
        this.name = name;
    }

    public String toString()
    {
        return "Esta es la foto: " + name + " con el uri: " + uri.toString();
    }
}