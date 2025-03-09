package com.example.contactbookappcompose.data.repository

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.data.remote.Person
import com.example.contactbookappcompose.data.remote.toContactMapper
import com.example.contactbookappcompose.domain.repository.ISUDirectoryRepo
import org.json.JSONArray

class ISUDirectoryRepoImpl : ISUDirectoryRepo {


    override suspend fun getContacts(
        context: Any,
        query: String,
        onResult: (List<Person>) -> Unit
    ) {
        val url = "https://apps.info.iastate.edu/mystate-api/v2/5684067410bcf5684067410c1a/search/$query"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,

            { response ->
                val personsArray = response.getJSONObject("data").getJSONArray("persons") ?: JSONArray()
                val results = mutableListOf<Person>()

                println("response : $response")
                println("personsArray : $personsArray")

                for (i in 0 until personsArray.length()) {
                    val obj = personsArray.getJSONObject(i)
                    val person = Person(
                        id = obj.getInt("id"),
                        netid = obj.getString("netid"),
                        firstName = obj.getString("firstName"),
                        lastName = obj.getString("lastName"),
                        email = obj.getString("email"),
                        classification = obj.getString("classification"),
                        isStaff = obj.getBoolean("isStaff"),
                        isStudent = obj.getBoolean("isStudent"),
                        major = obj.getString("major"),
                        middleName = obj.getString("middleName"),
                        title = obj.getString("title"),
                    )
                    results.add(person)
                    println("results : $results")
                }

                onResult(results)


            },

            { error -> Log.e("Volley", "Error fetching search results: ${error.message}") }
        )

        Volley.newRequestQueue(context as Context?).add(request)
    }
}