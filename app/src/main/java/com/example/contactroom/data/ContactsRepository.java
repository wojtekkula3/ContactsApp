package com.example.contactroom.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contactroom.model.Contacts;

import java.util.List;

// Klasa hub, która przyjmuje informacje z różnych baz danych - lokalnych lub online
// oraz decyduje jakie informacje przekazać dalej
public class ContactsRepository {

    private ContactsDao contactsDao;
    // private NetworkDatabase contactNet
    private LiveData<List<Contacts>> allContacts;

    public ContactsRepository(Application application)
    {
        ContactsRoomDatabase db = ContactsRoomDatabase.getDatabase(application);
        contactsDao = db.contactsDao();

        allContacts=contactsDao.getContacts();
    }

    public LiveData<List<Contacts>> getAllContacts()
    {
        return allContacts;
    }

    public void insertContact(Contacts contact)
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
}
