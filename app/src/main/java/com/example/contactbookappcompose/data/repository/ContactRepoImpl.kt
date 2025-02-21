package com.example.contactbookappcompose.data.repository

import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.domain.repository.ContactRepo
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class ContactRepoImpl : ContactRepo {

    val config = RealmConfiguration.create(schema = setOf(Contact::class))
    val realm: Realm = Realm.open(config)

    override suspend fun getContacts(): List<Contact> {
        return realm.query(Contact::class).find().toList()
    }

    override suspend fun addContact(contact: Contact) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContact(contact: Contact) {
        TODO("Not yet implemented")
    }

    override suspend fun updateContact(contact: Contact) {
        TODO("Not yet implemented")
    }

    override suspend fun getContact(): Contact {
        TODO("Not yet implemented")
    }
}