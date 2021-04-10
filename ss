[33mcommit 18c597cfee50bda700415d045246c3503e74e398[m[33m ([m[1;36mHEAD -> [m[1;32mfunctionality[m[33m)[m
Author: unknown <wojtekkula33@gmail.com>
Date:   Thu Apr 1 20:30:29 2021 +0200

    ListView

[1mdiff --git a/.idea/vcs.xml b/.idea/vcs.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..94a25f7[m
[1m--- /dev/null[m
[1m+++ b/.idea/vcs.xml[m
[36m@@ -0,0 +1,6 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8"?>[m
[32m+[m[32m<project version="4">[m
[32m+[m[32m  <component name="VcsDirectoryMappings">[m
[32m+[m[32m    <mapping directory="$PROJECT_DIR$" vcs="Git" />[m
[32m+[m[32m  </component>[m
[32m+[m[32m</project>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/com/example/contactroom/activities/MainActivity.java b/app/src/main/java/com/example/contactroom/activities/MainActivity.java[m
[1mindex dcb3102..05e099b 100644[m
[1m--- a/app/src/main/java/com/example/contactroom/activities/MainActivity.java[m
[1m+++ b/app/src/main/java/com/example/contactroom/activities/MainActivity.java[m
[36m@@ -8,6 +8,8 @@[m [mimport androidx.lifecycle.ViewModelProvider;[m
 import android.content.Intent;[m
 import android.os.Bundle;[m
 import android.util.Log;[m
[32m+[m[32mimport android.widget.ArrayAdapter;[m
[32m+[m[32mimport android.widget.ListView;[m
 import android.widget.TextView;[m
 [m
 import com.example.contactroom.R;[m
[36m@@ -15,6 +17,7 @@[m [mimport com.example.contactroom.databinding.ActivityMainBinding;[m
 import com.example.contactroom.model.Contacts;[m
 import com.example.contactroom.viewmodel.ContactsViewModel;[m
 [m
[32m+[m[32mimport java.util.ArrayList;[m
 import java.util.List;[m
 [m
 public class MainActivity extends AppCompatActivity {[m
[36m@@ -22,6 +25,8 @@[m [mpublic class MainActivity extends AppCompatActivity {[m
     private static final int NEW_CONTACT_ACTIVITY_REQUEST = 1;[m
     public ContactsViewModel contactsViewModel;[m
     private ActivityMainBinding binding;[m
[32m+[m[32m    private ArrayList<String> contactsArrayList;[m
[32m+[m[32m    private ArrayAdapter<String> arrayAdapter;[m
 [m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
[36m@@ -32,19 +37,21 @@[m [mpublic class MainActivity extends AppCompatActivity {[m
         contactsViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(ContactsViewModel.class);[m
 [m
         contactsViewModel.getAllContacts().observe(this, contacts -> {[m
[31m-[m
[31m-            StringBuilder tekst = new StringBuilder();[m
[32m+[m[32m            contactsArrayList = new ArrayList<>();[m
[32m+[m[32m            int index_listy=1;[m
             for (Contacts contact: contacts) {[m
[31m-                tekst.append(contact.getId()).append(". ").append(contact.getName()).append("\n");[m
[32m+[m[32m                contactsArrayList.add(index_listy+ ". " +contact.getName());[m
[32m+[m[32m                index_listy++;[m
             }[m
[31m-            binding.textView.setText(tekst);[m
[32m+[m[32m            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsArrayList);[m
[32m+[m[32m            binding.listView.setAdapter(arrayAdapter);[m
[32m+[m
         });[m
 [m
         binding.button.setOnClickListener(view -> {[m
             Intent intent = new Intent(this, AddContactActivity.class);[m
             startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST);[m
         });[m
[31m-[m
     }[m
 [m
     @Override[m
[36m@@ -55,8 +62,8 @@[m [mpublic class MainActivity extends AppCompatActivity {[m
         {[m
             String name = data.getStringExtra("name");[m
             String phoneNumber = data.getStringExtra("phoneNumber");[m
[31m-            Log.d("tagson", name);[m
[31m-            Log.d("tagson", phoneNumber);[m
[32m+[m[32m            Log.d("insertContactTAG", name);[m
[32m+[m[32m            Log.d("insertContactTAG", phoneNumber);[m
 [m
             Contacts contact = new Contacts(name, phoneNumber);[m
             ContactsViewModel.insertContact(contact);[m
[1mdiff --git a/app/src/main/java/com/example/contactroom/data/ContactsRepository.java b/app/src/main/java/com/example/contactroom/data/ContactsRepository.java[m
[1mindex 5617425..7be6c58 100644[m
[1m--- a/app/src/main/java/com/example/contactroom/data/ContactsRepository.java[m
[1m+++ b/app/src/main/java/com/example/contactroom/data/ContactsRepository.java[m
[36m@@ -37,4 +37,12 @@[m [mpublic class ContactsRepository {[m
 [m
         });[m
     }[m
[32m+[m
[32m+[m[32m    public void deleteAllContacts()[m
[32m+[m[32m    {[m
[32m+[m[32m        ContactsRoomDatabase.dataWriteExecutor.execute(()->{[m
[32m+[m[32m            contactsDao.deleteAll();[m
[32m+[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
 }[m
[1mdiff --git a/app/src/main/java/com/example/contactroom/viewmodel/ContactsViewModel.java b/app/src/main/java/com/example/contactroom/viewmodel/ContactsViewModel.java[m
[1mindex 5e932e6..47a0787 100644[m
[1m--- a/app/src/main/java/com/example/contactroom/viewmodel/ContactsViewModel.java[m
[1m+++ b/app/src/main/java/com/example/contactroom/viewmodel/ContactsViewModel.java[m
[36m@@ -32,4 +32,8 @@[m [mpublic class ContactsViewModel extends AndroidViewModel {[m
     {[m
         repository.insertContact(contacts);[m
     }[m
[32m+[m
[32m+[m[32m    public static void deleteAllContacts(){ repository.deleteAllContacts();}[m
[32m+[m
[32m+[m
 }[m
[1mdiff --git a/app/src/main/res/layout/activity_main.xml b/app/src/main/res/layout/activity_main.xml[m
[1mindex 35ebd75..837c9b7 100644[m
[1m--- a/app/src/main/res/layout/activity_main.xml[m
[1m+++ b/app/src/main/res/layout/activity_main.xml[m
[36m@@ -6,19 +6,6 @@[m
     android:layout_height="match_parent"[m
     tools:context=".activities.MainActivity">[m
 [m
[31m-    <TextView[m
[31m-        android:id="@+id/textView"[m
[31m-        android:layout_width="wrap_content"[m
[31m-        android:layout_height="wrap_content"[m
[31m-        android:text="Hello World!"[m
[31m-        android:textSize="17sp"[m
[31m-        app:layout_constraintBottom_toBottomOf="parent"[m
[31m-        app:layout_constraintHorizontal_bias="0.16"[m
[31m-        app:layout_constraintLeft_toLeftOf="parent"[m
[31m-        app:layout_constraintRight_toRightOf="parent"[m
[31m-        app:layout_constraintTop_toTopOf="parent"[m
[31m-        app:layout_constraintVertical_bias="0.07999998" />[m
[31m-[m
     <com.google.android.material.floatingactionbutton.FloatingActionButton[m
         android:id="@+id/button"[m
         android:layout_width="wrap_content"[m
[36m@@ -32,4 +19,15 @@[m
         app:layout_constraintVertical_bias="0.97"[m
         app:srcCompat="@android:drawable/ic_input_add" />[m
 [m
[32m+[m[32m    <ListView[m
[32m+[m[32m        android:id="@+id/listView"[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="match_parent"[m
[32m+[m[32m        app:layout_constraintBottom_toBottomOf="parent"[m
[32m+[m[32m        app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m        app:layout_constraintHorizontal_bias="0.0"[m
[32m+[m[32m        app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m        app:layout_constraintTop_toTopOf="parent"[m
[32m+[m[32m        app:layout_constraintVertical_bias="1.0" />[m
[32m+[m
 </androidx.constraintlayout.widget.ConstraintLayout>[m
\ No newline at end of file[m
