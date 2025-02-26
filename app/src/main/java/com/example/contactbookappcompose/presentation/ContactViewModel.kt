package com.example.contactbookappcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.data.repository.ContactRepoImpl
import com.example.contactbookappcompose.domain.contact.ContactData
import com.example.contactbookappcompose.domain.repository.ContactRepo
import com.example.contactbookappcompose.domain.utils.Resource
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class ContactViewModel : ViewModel() {
    var config: RealmConfiguration = RealmConfiguration.create(schema = setOf(Contact::class))
    var realm : Realm = Realm.open(config)
    val contactRepo: ContactRepo = ContactRepoImpl()


    //producer of state flow and collector of state flow defined simultaneously. collector is accessed from Views/composables

    //producer of flow
    private val _state = MutableStateFlow<ContactState>(ContactState())
    //collector of flow
    val state = _state.asStateFlow()


//    init {
//        viewModelScope.launch {
//            val result: Resource<List<Contact>> = repo.getContacts()
//            when(result) {
//               is Resource.Success ->{_state.emit(ContactState(contacts = result.data!!))}
//                is Resource.Error -> TODO()
//            }
//
//        }
//    }

    init {
        viewModelScope.launch {
            contactRepo.getContacts().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.emit(ContactState(contacts = result.data ?: emptyList()))
                        }
                        is Resource.Error -> {
                            TODO()
                        }
                    }
                }
        }
    }

    fun addContact(
        contact : ContactData
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.addContact(contact)
        }

        //the line below is not needed as when we use the asFlow extension on a realm query, it makes the
        // query "observable", i.e. if the result of the query changes, there will be a new emittion happening
        //AUTOMATICALLY (to all the collectors of that flow)
        //        _state.value = _state.value.copy( contacts = realm.query<Contact>().find())
    }

    fun deleteContact(contactId : ObjectId){

        if(contactId == null){
            throw IllegalArgumentException("id is required")
        }

        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.deleteContact(contactId)
        }
        _state.value = _state.value.copy( contacts = realm.query<Contact>().find())
    }

    fun findContactById(id: String): Contact? {
        val contact = realm.query<Contact>("id == $0", ObjectId(id)).first().find()
        return contact
    }

    fun editContact(contact: Contact, newContact: ContactData){
        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.updateContact(contact, newContact)
        }
    }
}