package com.example.contactbookappcompose.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contactbookappcompose.R
import com.example.contactbookappcompose.data.local.Contact
import org.mongodb.kbson.ObjectId

@Composable
fun ContactDetailScreen(
    id: String?,
    modifier: Modifier = Modifier,
    findContactById: (id: String) -> Contact?,
    deleteContact: (id: ObjectId) -> Unit,
    navigate: (route: String) -> Unit,
    popBackStack: () -> Unit
) {

    if(id == null){
        throw IllegalArgumentException("id is required")
    }

    val id = id.removePrefix("BsonObjectId(").removeSuffix(")")
    println("find contact with id $id and render its Details")
//
    val contact = findContactById(id)


    if(contact != null) {
        ContactDetails(
            modifier,
            contact,
            findContactById,
            deleteContact,
            navigate,
            popBackStack
            )
    }

}

@Composable
private fun ContactDetails(
    modifier: Modifier,
    contact: Contact,
    findContactById: (id: String) -> Contact?,
    deleteContact: (id : ObjectId) -> Unit,
    navigate: (route: String) -> Unit,
    popBackStack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25F)
                .background(Color.DarkGray)
        ) {

            Image(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "Contact Icon",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(256.dp)
            )
        }
        Spacer(modifier = Modifier.weight(0.2F))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65F)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${contact.firstName} ${contact.lastName}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ContactDetailRow(Icons.Default.Phone, contact.phoneNumber)
            ContactDetailRow(Icons.Default.Email, contact.email)
            ContactDetailRow(Icons.Default.LocationOn, contact.address)
            ContactDetailRow(Icons.Default.Info, contact.companyName)
        }

        Button(
            modifier=Modifier.fillMaxWidth(),
            onClick = {navigate("edit_contact/${contact.id}")}
        ) {
            Text("Edit Contact")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                deleteContact(contact.id)
                popBackStack()
            }
        ) { Text("Delete Contact") }

    }
}


@Composable
fun ContactDetailRow(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
