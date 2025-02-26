package com.example.contactbookappcompose.presentation.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.domain.contact.ContactData
import com.example.contactbookappcompose.presentation.ContactViewModel

@Composable
fun EditContactScreen(
    modifier: Modifier = Modifier,
    id : String?,
    onEditSave: (contact: Contact, newContact: ContactData) -> Unit,
    popBackStack: () -> Unit,
    navController: NavHostController,
    viewModel: ContactViewModel
) {
    if(id==null){throw IllegalArgumentException("id is required")}

    val id = id.removePrefix("BsonObjectId(").removeSuffix(")")
    println("EDIT contact with id $id. Inside EditContactScreen line 35")

    val contact = viewModel.findContactById(id)
        ?: throw IllegalArgumentException("contact with id $id not found")

    var firstName by remember{mutableStateOf(contact.firstName)}
    var lastName by remember{mutableStateOf(contact.lastName)}
    var phoneNumber by remember { mutableStateOf(contact.phoneNumber) }
    var email by remember { mutableStateOf(contact.email) }
    var address by remember { mutableStateOf(contact.address) }
    var companyName by remember { mutableStateOf(contact.companyName) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OutlinedTextField(
            value = firstName, //value = thisState
            onValueChange = {firstName = it}, //onClick changes a state, and 'value' is subscribed to that state so changes the 'value'
            label = { Text("First Name") }
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = {lastName = it},
            label = { Text("Last Name") }
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )
        OutlinedTextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Company Name") }
        )

        Button(
            onClick = {
                onEditSave(
                    contact,
                    ContactData(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber,
                        email = email,
                        address = address,
                        companyName = companyName
                    )
                    )
                popBackStack()
                      },
            modifier = Modifier
        ) {
            Text(text = "Save")
        }

    }
}