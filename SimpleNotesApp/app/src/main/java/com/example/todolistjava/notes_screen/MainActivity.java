package com.example.todolistjava.notes_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistjava.Note;
import com.example.todolistjava.NoteAdapter;
import com.example.todolistjava.R;
import com.example.todolistjava.add_note_screen.AddNoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNote;
    private FloatingActionButton floatingActionButton;
    private NoteAdapter noteAdapter;

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mainViewModel = new MainViewModel(getApplication());

        noteAdapter = new NoteAdapter();
        recyclerViewNote.setAdapter(noteAdapter);

        mainViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Note note = noteAdapter.getNotes().get(viewHolder.getAdapterPosition());
                        mainViewModel.remove(note);
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerViewNote);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.getIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recyclerViewNote = findViewById(R.id.recyclerViewNote);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }
}