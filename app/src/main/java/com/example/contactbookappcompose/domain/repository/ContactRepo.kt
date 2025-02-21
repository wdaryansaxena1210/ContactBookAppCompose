package com.example.contactbookappcompose.domain.repository

import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.domain.utils.Resource

interface ContactRepo {

    abstract suspend fun getContacts(): Resource<List<Contact>>
    abstract suspend fun addContact(contact: Contact)
    abstract suspend fun deleteContact(contact: Contact)
    abstract suspend fun updateContact(contact: Contact)
    abstract suspend fun getContact():Contact

}