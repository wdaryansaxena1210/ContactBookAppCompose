package com.example.contactbookappcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.presentation.ui.theme.ContactBookAppComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: ContactViewModel by viewModels()

//        CoroutineScope(Dispatchers.Main).launch {
//
//            println(contacts.toString())
//
//            contacts.data?.forEach { contact ->
//                println("Contact: ${contact.firstName} ${contact.lastName}")
//            }
//        }


        setContent {
            ContactBookAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        val state = viewModel.state.collectAsState()
                        if (state.value.errorMessage != null) {
                            Text("an error occurred : ${state.value.errorMessage}")
                        }
                        if (state.value.isLoading) {
                            Text("loading...")
                        } else {
                            Navigation(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Navigation(viewModel: ContactViewModel) {

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "contact_list_screen") {
            composable("contact_list_screen") {
                ContactListScreen(
                    state = viewModel.state.collectAsState(),
                    onClick = {contact
                        -> navController.navigate("contact_detail_screen/${contact.id}")
                    }
                )
            }
            composable("contact_detail_screen/{id}") {
                ContactDetailScreen(
                    id = it.arguments?.getString("id"),
                    realm = viewModel.realm
                )
            }

        }
    }
}

