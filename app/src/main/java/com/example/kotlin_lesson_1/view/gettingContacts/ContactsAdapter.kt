package com.example.kotlin_lesson_1.view.gettingContacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.ContactsDTO
import kotlinx.android.synthetic.main.item_contacts_recyclerview.view.*

class ContactsAdapter(items: List<ContactsDTO>, receivedContext: Context) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private var contactsList: List<ContactsDTO> = items
    private var context: Context = receivedContext

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.item_contacts_recyclerview,
                    parent, false
                ) as View
        )
    }

    override fun onBindViewHolder(
        holder: ContactsViewHolder,
        position: Int
    ) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    inner class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: ContactsDTO) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                itemView.contacts_item_name.text = data.name
                itemView.contacts_item_number.text = data.number
                itemView.contacts_item_image.setImageBitmap(data.image)

                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context, "Clicked on >>> ${data.name}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}