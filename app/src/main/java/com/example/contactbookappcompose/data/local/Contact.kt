package com.example.contactbookappcompose.data.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Contact : RealmObject {

    @PrimaryKey
    var id: ObjectId = ObjectId()
    var firstName: String = ""
    var lastName: String = ""
    var phoneNumber: String = ""
    var email: String = ""
    var address: String = ""
    var companyName: String = ""
}