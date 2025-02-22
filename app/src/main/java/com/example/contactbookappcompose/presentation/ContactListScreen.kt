package com.example.contactbookappcompose.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.contactbookappcompose.R
import com.example.contactbookappcompose.data.local.Contact

@Composable
fun ContactListScreen(state: State<ContactState>, onClick: (item : Contact) -> Unit) {

    LazyColumn{
        items(state.value.contacts){ item ->
            ContactItem(
                contact = item,
                onClick = onClick
            )
        }
    }

}

@Composable
fun ContactItem(contact: Contact, onClick: (item: Contact) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()){
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = { onClick(contact) }
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(4.dp).background(color = Color.hsv(
                0.4F,
                saturation = 0.4F,
                value = 0.8F), shape = RoundedCornerShape(20.dp))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "contact_picture",
                        modifier = Modifier.size(64.dp)
                    )
                }
                Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Bottom) {
                    Text(text = "${contact.firstName} ${contact.lastName}")
                    Text(text = "${contact.companyName}")
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().padding(4.dp)) {
                    Text(text = "${contact.phoneNumber}")
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}
