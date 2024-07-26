package com.example.todolistjava.add_note_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistjava.Note;
import com.example.todolistjava.R;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextView;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSave;
    private AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getIsFinish().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFinish) {
                if (isFinish) {
                    finish();
                }
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });
    }

    private void onSave() {
        String text = editTextView.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, R.string.toast_fill_in_the_field, Toast.LENGTH_LONG).show();
        } else {
            int priority = getPriority();
            addNoteViewModel.onSave(new Note(text, priority));

        }
    }

    private int getPriority() {
        int priority;
        if (radioButtonLow.isChecked()) {
            priority = 0;
        } else if (radioButtonMedium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    private void initView() {
        editTextView = findViewById(R.id.editTextView);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        buttonSave = findViewById(R.id.buttonSave);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}