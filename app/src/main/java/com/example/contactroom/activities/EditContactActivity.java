package com.example.contactroom.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.contactroom.databinding.ActivityEditContactBinding;
import com.example.contactroom.model.Contact;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class EditContactActivity extends AppCompatActivity {

    ActivityEditContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra("contact");
        Log.d("editActivity", contact.getName() + " " + contact.getNumber());

        binding.editName.setText(contact.getName());
        binding.editPhoneNumber.setText(contact.getNumber());

        binding.saveButton.setOnClickListener(view -> {

            if(!TextUtils.isEmpty(binding.editName.getText()) && !TextUtils.isEmpty(binding.editPhoneNumber.getText()) )
            {
                contact.setName(String.valueOf(binding.editName.getText()));
                contact.setNumber(String.valueOf(binding.editPhoneNumber.getText()));
                intent.putExtra("contact_edited", contact);
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                // Hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                Snackbar.make(binding.saveButton, "You need to put name and number", Snackbar.LENGTH_INDEFINITE).
                        setAction("OK", view1 -> {
                        }).show();
                setResult(RESULT_CANCELED, intent);
            }

        });

    }
}