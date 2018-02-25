package com.example.jdemu.pruebaselena;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    FloatingActionButton añadir;
    ArrayList<productos> todos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.fondo);
        añadir = (FloatingActionButton) findViewById(R.id.añadir);
        llenar();
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.opciones, null);
                builder.setView(v);
                final EditText edt = (EditText) v.findViewById(R.id.nameP);
                final EditText edt2 = (EditText) v.findViewById(R.id.amoundP);
                builder.setTitle("Añadir producto")
                        .setPositiveButton("ACEPTAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference();
                                        bbdd.child(String.valueOf(edt.getText())).push();
                                        bbdd.child(String.valueOf(edt.getText())).setValue(String.valueOf(edt2.getText()));
                                        llenar();


                                    }
                                })
                        .setNegativeButton("CANCELAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                AlertDialog b =builder.create();
                b.show();
            }

        });

    }

    public void llenar() {
        todos.clear();
        DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference();
        bbdd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final Bitmap[] bit = new Bitmap[1];
                FirebaseStorage storage = FirebaseStorage.getInstance();
                final StorageReference storageRef = storage.getReferenceFromUrl("gs://compa-elena.appspot.com/").child("huevos.jpg");
                final File localFile;
                try {
                    localFile = File.createTempFile("images", "jpg");
                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bit[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            Iterator<DataSnapshot> hijos = dataSnapshot.getChildren().iterator();
                            while (hijos.hasNext()) {
                                DataSnapshot son = hijos.next();
                                final String nombre = son.getKey();
                                final String cantidad = (String) son.getValue();
                                productos p = new productos(nombre, cantidad, bit[0]);
                                todos.add(p);

                            }
                            adaptadorlista adp = new adaptadorlista(MainActivity.this, todos);
                            lista.setAdapter(adp);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
