package com.example.contactbookappcompose.data.repository

import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.domain.contact.ContactData
import com.example.contactbookappcompose.domain.repository.ContactRepo
import com.example.contactbookappcompose.domain.utils.Resource
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mongodb.kbson.ObjectId

class ContactRepoImpl : ContactRepo {

    val config = RealmConfiguration.create(schema = setOf(Contact::class))
    val realm: Realm = Realm.open(config)


    //
    // The code below needed to be changed as the code below wont rerender the screen if
    // we add a contact to the database:
    //
//    override suspend fun getContacts(): Resource<List<Contact>> {
//        try {
//            val contacts = realm.query(Contact::class).find().toList()
//            val result = Resource.Success(
//                data = contacts
//            )
//            return result
//        }catch(e : Exception){
//            return Resource.Error(
//                message = e.message.toString()
//            )
//        }
//    }

    override suspend fun getContacts(): Flow<Resource<List<Contact>>> = flow {
        try {
            // Use Realm's Flow extension for querying the database
            realm.query(Contact::class).asFlow().collect { result ->
                val contacts = result.list
                emit(Resource.Success(contacts))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch contacts: ${e.message}"))
        }
    }


    override suspend fun addContact(contact: ContactData) {
            realm.write {
                copyToRealm(Contact().apply {
                    this.firstName = contact.firstName
                    this.lastName = contact.lastName
                    this.phoneNumber = contact.phoneNumber
                    this.email = contact.email
                    this.address = contact.address
                    this.companyName = contact.companyName
                })
            }
    }

    override suspend fun deleteContact(contactId : ObjectId) {
        realm.write{
            val contact = realm.query<Contact>("id == $0", contactId).find().firstOrNull()
            contact?.let { findLatest(it)?.let { delete(it) } }
        }
    }

    override suspend fun updateContact(contact: Contact, newContact: ContactData) {
        realm.write {
            val contactToEdit = findLatest(contact) ?: return@write
            println("New contact Email ${newContact.email}")
            contactToEdit.apply {
                this.email = newContact.email
                this.firstName = newContact.firstName
                this.lastName = newContact.lastName
                this.phoneNumber = newContact.phoneNumber
                this.address = newContact.address
                this.companyName = newContact.companyName
            }
            print("contact updated. New Company Name is ${contactToEdit.companyName}")
        }
    }

    override suspend fun findContactById(id: String): Contact? {
        val contact = realm.query<Contact>("id == $0", ObjectId(id)).first().find()
        return contact
    }

}