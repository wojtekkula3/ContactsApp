package com.example.contactroom.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactroom.data.ContactsDao;
import com.example.contactroom.data.ContactsRepository;
import com.example.contactroom.model.Contacts;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {

    public static ContactsRepository repository;
    public final LiveData<List<Contacts>> allContacts;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactsRepository(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contacts>> getAllContacts() {
        return allContacts;
    }

    public static void insertContact(Contacts contacts)
    {
        repository.insertContact(contacts);
    }
}
