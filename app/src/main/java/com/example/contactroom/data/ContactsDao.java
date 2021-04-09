package com.example.contactroom.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactroom.model.Contact;

import java.util.List;

// Interfejs która umożliwia nam działanie i wykonywanie zapytań na bazie danych
@Dao
public interface ContactsDao {

    @Query("SELECT * FROM contacts_table")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contacts_table WHERE contacts_table.id == :id")
    LiveData<Contact> getContact(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contacts_table")
    void deleteAll();

    @Update
    void update(Contact contact);
}
