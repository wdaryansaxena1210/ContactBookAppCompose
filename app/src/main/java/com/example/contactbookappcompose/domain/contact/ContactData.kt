package com.example.contactbookappcompose.domain.contact

data class ContactData(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val companyName: String
)
//proabably this class is not needed as using Realm. if we were using room this would be needed
//as the app/data/Contact class would be a DTO class
