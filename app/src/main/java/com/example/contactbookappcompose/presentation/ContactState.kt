package com.example.contactbookappcompose.presentation

import com.example.contactbookappcompose.data.local.Contact

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
