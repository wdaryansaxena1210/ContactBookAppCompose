package com.example.contactbookappcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.data.repository.ContactRepoImpl
import com.example.contactbookappcompose.domain.repository.ContactRepo
import com.example.contactbookappcompose.domain.utils.Resource
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    var config: RealmConfiguration = RealmConfiguration.create(schema = setOf(Contact::class))
    var realm : Realm = Realm.open(config)
    val repo: ContactRepo = ContactRepoImpl()


    //producer of state flow and collector of state flow defined simultaneously. collector is accessed from Views/composables

    //producer of flow
    private val _state = MutableStateFlow<ContactState>(ContactState())
    //collector of flow
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            val result: Resource<List<Contact>> = repo.getContacts()
            when(result) {
               is Resource.Success ->{_state.emit(ContactState(contacts = result.data!!))}
                is Resource.Error -> TODO()
            }

        }
    }
}