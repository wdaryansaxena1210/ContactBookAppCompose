package com.example.contactbookappcompose.presentation

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactbookappcompose.data.remote.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    context: Context,
    onSearch: (Context, String, (List<Person>) -> Unit) -> Unit,
    navigate: (firstName: String, lastName: String, email: String) -> Unit,
    ) {
    var searchText by remember { mutableStateOf("") }
    val searchResults = remember { mutableStateListOf<Person>() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = {
                onSearch(context, searchText) { results ->
                searchResults.clear()
                searchResults.addAll(results)
            }
                println("onSearch")
            },
            active = false,
            onActiveChange = {},
            placeholder = { Text("Add From ISU Directory") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    modifier = Modifier.clickable { searchText = "" }
                )
            },
            content = {},
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(searchResults) { person ->
                PersonCard(person, navigate)
            }
        }
    }
}

@Composable
fun PersonCard(person: Person, onClick : (String, String, String)->Unit ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(person.firstName, person.lastName, "${person.netid}@iastate.edu") },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = person.firstName + " " + person.lastName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "NetID: ${person.netid}", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Email: ${person.email}", fontSize = 14.sp)
            Text(text = "Major: ${person.major}", fontSize = 14.sp)
        }
    }
}