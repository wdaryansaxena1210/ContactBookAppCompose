package com.example.contactbookappcompose.data.remote

import com.example.contactbookappcompose.data.local.Contact

data class Person(
    val addresses: List<Addresses> = emptyList(),
    val classification: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val isStaff: Boolean,
    val isStudent: Boolean,
    val lastName: String,
    val major: String,
    val middleName: String,
    val netid: String,
    val title: String
)

fun Person.toContactMapper(): Contact {
    return Contact().apply {
        this.firstName = this@toContactMapper.firstName
        this.lastName = this@toContactMapper.lastName
        this.phoneNumber = this@toContactMapper.netid
        this.email = this@toContactMapper.email
    }
}


