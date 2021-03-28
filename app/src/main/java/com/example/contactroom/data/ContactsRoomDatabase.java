package com.example.contactroom.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.contactroom.model.Contacts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// WAŻNE - Dzięki dataWriteExecutor wykonuje wszystkie operacje nie na wątku głównym,
// tylko na wątku który dzieje się w tle, co nie obciąża naszej aplikacji i pozwala
// LiveData jako obserwatorowi wyświetlać powiadomienia gdy coś się zmieni



// Klasa abstrakcyjna, ponieważ jest taki jest wymóg, by nie można było utworzyć instancji tej klasy
@Database(entities = {Contacts.class}, version = 1, exportSchema = false)
public abstract class ContactsRoomDatabase extends RoomDatabase {

    public abstract ContactsDao contactsDao();

    // Volatile Zmienna ulotna – zmienna lub obiekt, które mogą zostać zmienione "z zewnątrz" — niezależnie od kodu programu, w którym się znajdują.
    public static volatile ContactsRoomDatabase INSTANCE;

    // Executor service pomaga działać mechanizmom na background wątku
    // Zapytania bazy danych (dodawanie, edytowanie) są wykonywane na pobocznym watku, ponieważ są czasochłonne i mogą spowalniać włówną aplikacje
    public static final ExecutorService dataWriteExecutor = Executors.newFixedThreadPool(4);


    // Zwraca tylko jedną instancje bazy danych, jeśli jeszcze jej nie mamy stworzy ją
    public static ContactsRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (ContactsRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Stworzenie bazy danych oraz wykonanie sRoomDatabaseCallback
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactsRoomDatabase.class, "contacts_database")
                            .addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    // Funkcja wywoływana przy utworzeniu instancji bazy danych
    private static final RoomDatabase.Callback sRoomDatabaseCallBack =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    // Wykona się przy tworzeniu bazy danych na wątku w tle
                    dataWriteExecutor.execute(()->{
                        ContactsDao contactsDao = INSTANCE.contactsDao();
                        contactsDao.deleteAll();

                        Contacts contacts = new Contacts("Karol","123456789");
                        Contacts contacts2 = new Contacts("Marcin","123456788");
                        contactsDao.insert(contacts);
                        contactsDao.insert(contacts2);
                    });
                }
            };
}
