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
import com.example.contactbookappcompose.R
import com.example.contactbookappcompose.data.local.Contact
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId

@Composable
fun ContactDetailScreen(id: String?, modifier: Modifier = Modifier, realm : Realm) {

    if(id == null){
        throw IllegalArgumentException("id is required")
    }

    val id = id.removePrefix("BsonObjectId(").removeSuffix(")")
    println(id)

    val contact = realm.query<Contact>("id == $0", ObjectId(id)).find().first()

    ContactDetails(modifier, contact)


}

@Composable
private fun ContactDetails(
    modifier: Modifier,
    contact: Contact
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
