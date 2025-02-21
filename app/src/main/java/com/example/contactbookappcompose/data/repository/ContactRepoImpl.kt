package com.example.contactbookappcompose.data.repository

import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.domain.repository.ContactRepo
import com.example.contactbookappcompose.domain.utils.Resource
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class ContactRepoImpl : ContactRepo {

    val config = RealmConfiguration.create(schema = setOf(Contact::class))
    val realm: Realm = Realm.open(config)

    override suspend fun getContacts(): Resource<List<Contact>> {
        try {
            val contacts = realm.query(Contact::class).find().toList()
            val result = Resource.Success(
                data = contacts
            )
            return result
        }catch(e : Exception){
            return Resource.Error(
                message = e.message.toString()
            )
        }
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