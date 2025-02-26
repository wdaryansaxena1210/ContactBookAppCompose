package com.example.contactbookappcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactbookappcompose.presentation.ui.theme.ContactBookAppComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Contacts") },
                            actions = {
                                IconButton(onClick = {
                                    navController.navigate("add_contact")
                                }) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
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
                            Navigation(viewModel = viewModel, navController = navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Navigation(viewModel: ContactViewModel, navController: NavHostController) {

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
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable("add_contact") {
                AddContactScreen(
                    navController = navController,
                    realm = viewModel.realm,
                    viewModel = viewModel)
            }


        }
    }
}

