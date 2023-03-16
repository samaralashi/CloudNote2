package com.example.cloudnote22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements NoteAdapter.ItemClickListener, NoteAdapter.ItemClickListener2 {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Note> items;
    NoteAdapter[] myListData;
    NoteAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    ImageView delete;
    EditText updateTitle;
    EditText updateContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        updateTitle = findViewById(R.id.update_title);
        updateContent = findViewById(R.id.update_content);

        // update = findViewById(R.id.btn_update);
        EditText edit_name;
        rv = findViewById(R.id.rvRest);
        items = new ArrayList<Note>();
        adapter = new NoteAdapter(this, items, this, this);
        delete = findViewById(R.id.delete);
        GetAllProducts();

    }

    private void GetAllProducts() {


        db.collection("Notes").get()
                .addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        Log.d("smr", "onSuccess: LIST EMPTY");
                        return;
                    } else {
                        for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                            if (documentSnapshot.exists()) {
                                String id = documentSnapshot.getId();
                                String title = documentSnapshot.getString("Note Title");
                                String content = documentSnapshot.getString("Note Content");

                                Note note = new Note(id, title, content);
                                items.add(note);

                                rv.setLayoutManager(layoutManager);
                                rv.setHasFixedSize(true);
                                rv.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                                Log.e("LogDATA", items.toString());

                            }
                        }
                    }

                }).addOnFailureListener(e -> Log.e("LogDATA", "get failed with "));

    }
    public void deleteNote(final Note note) {

    }
    public void updateNote(final Note note) {
    }


        @Override
    public void onItemClick(int position, String id) {
        deleteNote(items.get(position));
    }

    @Override
    public void onItemClick2(int position, String id) {
        updateNote(items.get(position));

    }

}
