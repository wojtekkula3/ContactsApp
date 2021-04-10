package com.example.contactroom.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactroom.R;
import com.example.contactroom.model.Contact;

import java.util.List;

public class ContactListRecyclerViewAdapter extends RecyclerView.Adapter<ContactListRecyclerViewAdapter.ViewHolder> {

    private OnContactClickListener onContactClickListener;
    private List<Contact> contactsList;
    private Context context;

    public ContactListRecyclerViewAdapter(Context context, List<Contact> contactsList, OnContactClickListener onContactClickListener)
    {
        this.context = context;
        this.contactsList = contactsList;
        this.onContactClickListener=onContactClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view, onContactClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListRecyclerViewAdapter.ViewHolder holder, int position) {
        Contact contact = new Contact(contactsList.get(position).getName(), contactsList.get(position).getNumber());
        holder.name.setText(contact.getName());
        holder.number.setText(contact.getNumber());


    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnContactClickListener onContactClickListener;
        TextView name, number;
        Button delete, edit;

        public ViewHolder(@NonNull View itemView, OnContactClickListener onContactClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_nameTextView);
            number =itemView.findViewById(R.id.row_phoneNumberTextView);
            delete = itemView.findViewById(R.id.deleteButton);
            edit = itemView.findViewById(R.id.editButton);
            this.onContactClickListener = onContactClickListener;

            itemView.setOnClickListener(this::onClick);
            delete.setOnClickListener(this::onDeleteClick);
            edit.setOnClickListener(this::onEditClick);
        }

        @Override
        public void onClick(View view) {
            onContactClickListener.onContactClick(getAdapterPosition());
        }

        public void onDeleteClick(View view) {
            onContactClickListener.onDeleteButtonClick(getAdapterPosition());
        }

        public void onEditClick(View view)
        {
            onContactClickListener.onEditButtonClick(getAdapterPosition());
        }
    }

    public interface OnContactClickListener{
        void onContactClick(int position);

        void onDeleteButtonClick(int position);

        void onEditButtonClick(int position);
    }
}
