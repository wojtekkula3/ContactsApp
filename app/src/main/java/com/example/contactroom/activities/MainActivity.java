package com.example.contactroom.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.contactroom.adapters.ContactListRecyclerViewAdapter;
import com.example.contactroom.databinding.ActivityMainBinding;
import com.example.contactroom.model.Contact;
import com.example.contactroom.viewmodel.ContactsViewModel;

public class MainActivity extends AppCompatActivity implements ContactListRecyclerViewAdapter.OnContactClickListener {

    private static final int NEW_CONTACT_ACTIVITY_REQUEST = 1;
    public ContactsViewModel contactsViewModel;
    private ActivityMainBinding binding;
    private ContactListRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactsViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(ContactsViewModel.class);
        contactsViewModel.getAllContacts().observe(this, contacts -> {

            // Set Adapter
            adapter = new ContactListRecyclerViewAdapter(this, contacts, this);
            binding.contactsRecyclerView.setAdapter(adapter);


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
            Log.d("insertContactTAG", name);
            Log.d("insertContactTAG", phoneNumber);

            Contact contact = new Contact(name, phoneNumber);
            ContactsViewModel.insertContact(contact);
        }
    }

    @Override
    public void onContactClick(int position) {
        Log.d("onClickTAG", "onContactClick: "+ position+" clicked");

    }

    @Override
    public void onDeleteButtonClick(int position) {
        Log.d("onClickTAG", "onContactClick: "+ position + " deleted");
    }

    public void onEditButtonClick(int position)
    {

    }
}