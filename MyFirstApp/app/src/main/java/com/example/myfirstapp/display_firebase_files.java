package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class display_firebase_files extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static FirebaseStorage FIRE_STORAGE ;
    private static final String MY_FOLDER = "CAMF";
    private static  String MY_PATH=MY_FOLDER+"/";
    private List<firebaseItem> FIREBASE_DATA = new ArrayList<firebaseItem>();;
    private static PhotoView PHOTO_DISPLAYER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_firebase_files);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(myToolbar);
        FIRE_STORAGE = FirebaseStorage.getInstance("gs://ndandroid-332aa.appspot.com");
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_firebase_files);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        PHOTO_DISPLAYER = (PhotoView)findViewById(R.id.photo_view_fb_list);

        PHOTO_DISPLAYER.setVisibility(View.INVISIBLE);
        PHOTO_DISPLAYER.setBackgroundColor(Color.BLACK);

        mAdapter = new MyFirebaseFilesAdapter(FIREBASE_DATA,FIRE_STORAGE,PHOTO_DISPLAYER);
        recyclerView.setAdapter(mAdapter);

        // specify an adapter (see also next example)
        getFirebaseFilesDataAsync();

    }

    @Override
    public void onBackPressed() {
        if(PHOTO_DISPLAYER.getVisibility()== View.VISIBLE)
            PHOTO_DISPLAYER.setVisibility(View.INVISIBLE);
        else
            super.onBackPressed();
    }

    public void getFirebaseFilesData(String path)
    {
        FIREBASE_DATA  = new ArrayList<firebaseItem>();
        StorageReference listRef = FIRE_STORAGE.getReference().child(path);
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            addFileToList(prefix,false);
                        }

                        for (StorageReference item : listResult.getItems()) {
                            addFileToList(item,true);
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

    public void getFirebaseFilesDataAsync()
    {
        // An Async task always executes in new thread
        new Thread(new Runnable() {
            public void run()
            {
                Log.println(Log.ASSERT,"h","Entro");
                FIREBASE_DATA  = new ArrayList<firebaseItem>();
                Log.println(Log.ASSERT,"h","Antes de pedir la referencia ");
                StorageReference listRef = FIRE_STORAGE.getReference().child(MY_FOLDER);
                Log.println(Log.ASSERT,"h","Despues de pedir la referencia");
                listRef.listAll()
                        .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {
                                for (StorageReference prefix : listResult.getPrefixes()) {
                                    addFileToList(prefix,false);
                                }

                                for (StorageReference item : listResult.getItems()) {
                                    addFileToList(item,true);
                                }
                                Log.println(Log.ASSERT,"h",((MyFirebaseFilesAdapter)mAdapter).getItemCount()+"");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Uh-oh, an error occurred!
                            }
                        });
                Log.println(Log.ASSERT,"h",((MyFirebaseFilesAdapter)mAdapter).getItemCount()+"");
            }
        }).start();
    }

    public void addFileToList(final StorageReference element, final boolean isFile)
    {
        // An Async task always executes in new thread
        new Thread(new Runnable() {
            public void run()
            {
                FIRE_STORAGE.getReference(element.getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ((MyFirebaseFilesAdapter)mAdapter).addSelfie(new firebaseItem(
                                element.getName(),
                                element.getPath(),
                                uri.toString(),
                                isFile));
                        mAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.println(Log.ASSERT,"h","Fallo " + element.getPath());
                    }
                });
            }
        }).start();
    }
}
