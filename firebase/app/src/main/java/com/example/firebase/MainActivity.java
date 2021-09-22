package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fbAuth;
    DatabaseReference fbDB;

    String MAIL = "tobyobito@gmail.com";
    String PASSWORD = "dqkmeioqdqum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbAuth = FirebaseAuth.getInstance();
        fbDB = FirebaseDatabase.getInstance().getReference();
        guardar();z
    }

    public void borrar()
    {
        fbDB.child("mitable").child("133").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    long size = snapshot.getChildrenCount();
                    Log.println(Log.ASSERT,"Mail", "Total: " +size);
                    String mail = snapshot.toString();
                    Log.println(Log.ASSERT,"Mail", "El mail es: " +mail);
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void buscarEscuchando()
    {
        fbDB.child("mitable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    long size = snapshot.getChildrenCount();
                    Log.println(Log.ASSERT,"Mail", "Total: " +size);
                    String mail = snapshot.child("131").toString();
                    Log.println(Log.ASSERT,"Mail", "El mail es: " +mail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void buscar()
    {
        fbDB.child("mitable").child("133").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    long size = snapshot.getChildrenCount();
                    Log.println(Log.ASSERT,"Mail", "Total: " +size);
                    String mail = snapshot.toString();
                    Log.println(Log.ASSERT,"Mail", "El mail es: " +mail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void registrar()
    {
        fbAuth.createUserWithEmailAndPassword(MAIL,PASSWORD).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"Cuenta creada de forma exitosa", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"No se pudo crear la cuenta", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void guardar()
    {
        Map<String, Object> datos = new HashMap<>();
        datos.put("mai",MAIL);
        datos.put("password",PASSWORD);
        fbDB.child("mitable").child("131").setValue(datos).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Log.println(Log.ASSERT,"TodoOk", "Error al guardar datos en DB");
                        }
                        else{
                            Log.println(Log.ASSERT,"DBError", "Error al guardar datos en DB");
                        }
                    }
                }
        );
    }

    public void consultar()
    {
        fbDB.child("mitable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String mail = snapshot.child("nombre").toString();
                    Log.println(Log.ASSERT,"Nombre", "El mail es: " +mail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}