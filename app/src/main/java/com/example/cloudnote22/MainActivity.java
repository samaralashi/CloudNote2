package com.example.cloudnote22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextTitleNote;
    EditText editTextContentNote;
    Button buttonSave;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitleNote =  findViewById(R.id.title_note);
        editTextContentNote =  findViewById(R.id.content_note);
        buttonSave = findViewById(R.id.btn_save);


    }
    public void saveToFirebase(View v) {
        String titleNote = editTextTitleNote.getText().toString();
        String contentNote = editTextContentNote.getText().toString();

        Map<String, Object> note = new HashMap<>();
        if (!titleNote.isEmpty() && !contentNote.isEmpty()) {

            note.put("Title Note", titleNote);
            note.put("Content Note", contentNote);

// Add a new document with a generated ID
            db.collection("Notes")
                    .add(note)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            openActivity2();

//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(MainActivity.this, "DocumentSnapshot added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error adding document", e);
                        }
                    });

        } else {
            Toast.makeText(this, "Please Fill fields", Toast.LENGTH_SHORT).show();
        }    }

    public void openActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    }