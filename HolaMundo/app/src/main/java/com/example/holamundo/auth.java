package com.example.holamundo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class auth extends AppCompatActivity {

    FirebaseAuth fbAuth;
    DatabaseReference fbDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        fbAuth =FirebaseAuth.getInstance();
        fbDB = FirebaseDatabase.getInstance().getReference();
        selectAll();

    }

    public void login(View view){
        EditText etMail = (EditText) findViewById(R.id.etMail);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String mail = etMail.getText().toString();
        String pass = etPassword.getText().toString();
        Log.println(Log.ASSERT, "Registro", "Prevalidacion");
        if( !mail.isEmpty() &&  !pass.isEmpty())
        {
            if(pass.length()>6)
            {
                Log.println(Log.ASSERT, "Registro", "Prevalidacion completada");
                login(mail,pass);
            }

        }
    }

    public void login(String mail, String pass)
    {
        fbAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(
            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Log.println(Log.ASSERT, "Registro", "Hemos registrado exitosamente al cliente");
                        insertar(mail);
                    }
                    else
                    {
                        Log.println(Log.ASSERT, "Registro NOK", "Error al regristrar al cliente ");

                    }
                }
            }
        );
    }

    public void insertar(String mail)
    {
        Map<String,Object> datosContacto = new HashMap<>();
        datosContacto.put("mail",mail);
        datosContacto.put("nombre","Federico");
        datosContacto.put("apellido","Smith");
        datosContacto.put("genero","M");

        fbDB.child("usuarios").child("2").setValue(datosContacto).addOnCompleteListener(
            new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.println(Log.ASSERT, "Registro", "Hemos registrado exitosamente al cliente en la DB");
                        selectFilter();
                    }
                    else
                    {
                        Log.println(Log.ASSERT, "Registro NOK", "Error al regristrar al cliente en la DB");

                    }
                }
            }
        );

    }

    public void selectAll()
    {
        fbDB.child("USUARIOS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.println(Log.ASSERT, "Registro Numero", "Son " + snapshot.getChildrenCount());

                String registro = snapshot.child("2").toString();

                Log.println(Log.ASSERT, "Registro Values", "Valores " + registro);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void selectFilter(){
        fbDB.child("USUARIOS").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String registro = snapshot.toString();

                    Log.println(Log.ASSERT, "Registro Values", "Valores 1 sólo elemento" + registro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void delete()
    {
        fbDB.child("USUARIOS").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String registro = snapshot.toString();

                    Log.println(Log.ASSERT, "Registro Values a borrar", "Valores 1 sólo elemento" + registro);

                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
