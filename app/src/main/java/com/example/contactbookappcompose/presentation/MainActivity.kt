package com.example.contactbookappcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.data.repository.ContactRepoImpl
import com.example.contactbookappcompose.domain.repository.ContactRepo
import com.example.contactbookappcompose.domain.utils.Resource
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

        val viewModel : ContactViewModel by viewModels()

        CoroutineScope(Dispatchers.Main).launch {

//            println(contacts.toString())

//            contacts.data?.forEach { contact ->
//                println("Contact: ${contact.firstName} ${contact.lastName}")
//            }
        }


        setContent {
            ContactBookAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
//                        Text(text = "Hello")
//                        println( "state is ${viewModel.state}")
                        Column {
                            viewModel.state.collectAsState().value.contacts.forEach{ contact ->
                                Text("Contact: ${contact.firstName} ${contact.lastName}")
                            }
                        }
                    }
                }
            }
        }
    }
}

