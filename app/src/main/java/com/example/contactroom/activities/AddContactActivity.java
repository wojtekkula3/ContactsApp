package com.example.contactroom.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.contactroom.databinding.ActivityAddContactBinding;

public class AddContactActivity extends AppCompatActivity {

    ActivityAddContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveButton.setOnClickListener(view -> {

            Intent intent = new Intent();
            String name = binding.name.getText().toString();
            String phoneNumber = binding.phoneNumber.getText().toString();

            if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneNumber))
            {
                intent.putExtra("name", binding.name.getText().toString());
                intent.putExtra("phoneNumber", binding.phoneNumber.getText().toString());
                setResult(RESULT_OK, intent);
            }
            else
                setResult(RESULT_CANCELED, intent);

            finish();
        });

    }

}