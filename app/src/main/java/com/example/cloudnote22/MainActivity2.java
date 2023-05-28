package com.example.cloudnote22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements NoteAdapter.ItemClickListener, NoteAdapter.ItemClickListener2{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Note> items;
    NoteAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    EditText updateTitle;
    ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        updateTitle = findViewById(R.id.update_title);
        rv = findViewById(R.id.rv);
        items = new ArrayList<Note>();
        adapter = new NoteAdapter(this, items, this, this);
        delete = findViewById(R.id.delete);
        GetAllNotes();

        Button addNote = findViewById(R.id.addBtn);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, AddNoteActivity.class));
            }
        });
    }

    private void GetAllNotes() {

        db.collection("Notes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("drn", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String note = documentSnapshot.getString("note");
                                    Note note1= new Note(id, note);
                                    items.add(note1);
                                    rv.setLayoutManager(layoutManager);
                                    rv.setHasFixedSize(true);
                                    rv.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    Log.e("samar", items.toString());
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("samar", "get failed with ");


                    }
                });
    }


    public void Delete(final Note note) {
        db.collection("Notes").document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("samar", "deleted");
                        Toast.makeText(MainActivity2.this, "deleted successfully", Toast.LENGTH_SHORT).show();
                        items.remove(note);
                        // Notify the adapter about the item removal
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("sara", "fail");
                    }
                });
    }

    public void updateNote(final Note note) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name");
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(customLayout);

        // Prepopulate the EditText with the current note title
        updateTitle = customLayout.findViewById(R.id.update_title);
        updateTitle.setText(note.getTitle());

        builder.setPositiveButton(
                "Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateTitle = customLayout.findViewById(R.id.update_title);

                        db.collection("Notes").document(note.getId()).update("note", updateTitle.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("samar", "DocumentSnapshot successfully updated!");
                                        Toast.makeText(MainActivity2.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
                                        note.setTitle(updateTitle.getText().toString());
                                        // Notify the adapter about the data change
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("samar", "Error updating", e);
                                    }
                                });
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onItemClick(int position, String id) {
        Delete(items.get(position));
    }

    @Override
    public void onItemClick2(int position, String id) {
        updateNote(items.get(position));
    }

}
