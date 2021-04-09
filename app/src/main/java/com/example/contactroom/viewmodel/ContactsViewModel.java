package com.example.contactroom.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactroom.data.ContactsRepository;
import com.example.contactroom.model.Contact;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {

    public static ContactsRepository repository;
    public final LiveData<List<Contact>> allContacts;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactsRepository(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<Contact> getOneContact(int id){ return repository.getOneContact(id); }

    public static void insertContact(Contact contact)
    {
        repository.insertContact(contact);
    }

    public static void deleteAllContacts(){ repository.deleteAllContacts();}

    public static void deleteOneContact(Contact contact){ repository.deleteOneContact(contact);}

    public static void updateContact(Contact contact) {repository.updateContact(contact);}

}
