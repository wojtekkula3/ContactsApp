package com.example.contactroom.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contactroom.model.Contact;

import java.util.List;

// Klasa hub, która przyjmuje informacje z różnych baz danych - lokalnych lub online
// oraz decyduje jakie informacje przekazać dalej
public class ContactsRepository {

    private ContactsDao contactsDao;
    // private NetworkDatabase contactNet
    private LiveData<List<Contact>> allContacts;

    public ContactsRepository(Application application)
    {
        ContactsRoomDatabase db = ContactsRoomDatabase.getDatabase(application);
        contactsDao = db.contactsDao();

        allContacts=contactsDao.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts()
    {
        return allContacts;
    }

    public LiveData<Contact> getOneContact(int id){return contactsDao.getContact(id); }

    public void insertContact(Contact contact)
    {
        //Operacje wczytania lub zapisu powinno wykonywać się poza wątkiem głównym dlatego używa się dataWriteExecutor
        ContactsRoomDatabase.dataWriteExecutor.execute(()->{
            contactsDao.insert(contact);

        });
    }

    public void deleteAllContacts()
    {
        ContactsRoomDatabase.dataWriteExecutor.execute(()->{
            contactsDao.deleteAll();

        });
    }

    public void deleteOneContact(Contact contact)
    {
        ContactsRoomDatabase.dataWriteExecutor.execute(()->{
            contactsDao.delete(contact);
        });
    }

    public void updateContact(Contact contact)
    {
        ContactsRoomDatabase.dataWriteExecutor.execute(()->{
            contactsDao.update(contact);
        });
    }
}
