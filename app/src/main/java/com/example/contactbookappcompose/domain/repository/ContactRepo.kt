package com.example.contactbookappcompose.domain.repository

import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.domain.contact.ContactData
import com.example.contactbookappcompose.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface ContactRepo {

    abstract suspend fun getContacts(): Flow<Resource<List<Contact>>>
    abstract suspend fun addContact(contact: ContactData)
    abstract suspend fun deleteContact(contactId: ObjectId)
    abstract suspend fun updateContact(contact: Contact, newContact: ContactData)
    abstract suspend fun findContactById(id: String): Contact?

}