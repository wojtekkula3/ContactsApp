package com.example.contactroom.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.contactroom.R;
import com.example.contactroom.databinding.ActivityMainBinding;
import com.example.contactroom.model.Contacts;
import com.example.contactroom.viewmodel.ContactsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_CONTACT_ACTIVITY_REQUEST = 1;
    public ContactsViewModel contactsViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactsViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(ContactsViewModel.class);

        contactsViewModel.getAllContacts().observe(this, contacts -> {

            StringBuilder tekst = new StringBuilder();
            for (Contacts contact: contacts) {
                tekst.append(contact.getId()).append(". ").append(contact.getName()).append("\n");
            }
            binding.textView.setText(tekst);
        });

        binding.button.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==NEW_CONTACT_ACTIVITY_REQUEST && resultCode==RESULT_OK)
        {
            String name = data.getStringExtra("name");
            String phoneNumber = data.getStringExtra("phoneNumber");
            Log.d("tagson", name);
            Log.d("tagson", phoneNumber);

            Contacts contact = new Contacts(name, phoneNumber);
            ContactsViewModel.insertContact(contact);
        }
    }
}