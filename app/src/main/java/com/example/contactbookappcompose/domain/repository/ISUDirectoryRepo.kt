package com.example.contactbookappcompose.domain.repository

import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.data.remote.ISUContactDTO
import com.example.contactbookappcompose.data.remote.Person
import com.example.contactbookappcompose.domain.utils.Resource

interface ISUDirectoryRepo {
    suspend fun getContacts(context: Any, query: String, onResult: (List<Person>) -> Unit)
}