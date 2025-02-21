package com.example.contactbookappcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.data.repository.ContactRepoImpl
import com.example.contactbookappcompose.domain.repository.ContactRepo
import com.example.contactbookappcompose.presentation.ui.theme.ContactBookAppComposeTheme
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CoroutineScope(Dispatchers.Main).launch {
            val config = RealmConfiguration.create(schema = setOf(Contact::class))
            val realm: Realm = Realm.open(config)

            val repo: ContactRepo = ContactRepoImpl()

            val contacts: List<Contact> = repo.getContacts()
            contacts.forEach { contact ->
                println("Contact: ${contact.firstName} ${contact.lastName}")
            }
        }


        setContent {
            ContactBookAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Hello")
                    }
                }
            }
        }
    }
}

