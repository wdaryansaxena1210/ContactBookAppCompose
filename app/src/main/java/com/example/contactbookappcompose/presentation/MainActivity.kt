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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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
                    },
                    bottomBar = { BottomNavigationBar(navController) },

                ) { innerPadding ->

                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()) {



                        Box(
                            modifier = Modifier.fillMaxSize(),
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
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                label = { Text("Search") },
                selected = false,
                onClick = { navController.navigate("search_screen") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Contacts") },
                label = { Text("Contacts") },
                selected = false,
                onClick = { navController.navigate("contact_list_screen") }
            )
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
                    findContactById = viewModel::findContactById,
                    deleteContact = viewModel::deleteContact,
                    navigate = navController::navigate,
                    popBackStack = navController::popBackStack
                )
            }
            composable("add_contact") {it->
//                val firstName = it.arguments?.getString("firstName")?:""
//                val lastName = it.arguments?.getString("lastName")?:""
//                val phoneNumber = it.arguments?.getString("phoneNumber")?:""
//                val email = it.arguments?.getString("email")?:""
                AddContactScreen(
                    addContact = viewModel::addContact,
                    popBackStack = navController::popBackStack,
                )
            }
            composable("edit_contact/{id}") { it ->
                val id = it.arguments?.getString("id")
                EditContactScreen(
                    id = id,
                    onEditSave = viewModel::editContact,
                    popBackStack = navController::popBackStack,
                    findContactById = viewModel::findContactById
                )
            }
            composable("search_screen") {
                SearchScreen(
                    context = this@MainActivity,
                    viewModel::searchContact,
                    navigate = {
                        firstName:String,
                        lastName:String,
                        email:String ->
                        navController.navigate("add_contact/$firstName/$lastName/$email")},
                )
            }
            composable("add_contact/{firstName}/{lastName}/{email}") { it ->
                val firstName = it.arguments?.getString("firstName") ?: ""
                val lastName = it.arguments?.getString("lastName") ?: ""
                val email = it.arguments?.getString("email") ?: ""
                AddContactScreen(
                    addContact = viewModel::addContact,
                    popBackStack = navController::popBackStack,
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
            }
        }
    }
}


//                        SearchBar(
//                            modifier = Modifier.fillMaxWidth(),
//                            query = "",
//                            onQueryChange = { println("on query change") },
//                            onSearch = { println("onSearch") },
//                            active = false,
//                            onActiveChange = {println("onActiveChange")},
//                            placeholder = { Text("Add From ISU Directory") },
//                            leadingIcon = {
//                                Icon    (
//                                    imageVector = Icons.Default.Search,
//                                    contentDescription = "Search Icon"
//                                )
//                            },
//                            trailingIcon = {
//                                Icon(
//                                    modifier = Modifier.clickable {
////                                        searchText = ""
//                                    },
//                                    imageVector = Icons.Default.Close,
//                                    contentDescription = "Close Icon"
//                                )
//                            }
//
//                        ) { }
