package com.example.contactroom.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.contactroom.model.Contacts;

import java.util.List;

// Interfejs która umożliwia nam działanie i wykonywanie zapytań na bazie danych
@Dao
public interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contacts contact);

    @Delete
    void delete(Contacts contacts);

    @Query("DELETE FROM contacts_table")
    void deleteAll();

    @Query("SELECT * FROM contacts_table")
    LiveData<List<Contacts>> getContacts();
}
