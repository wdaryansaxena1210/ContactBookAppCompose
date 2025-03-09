package com.example.contactbookappcompose.data.remote

data class Metadata(
    val current_page: Int,
    val next: Any,
    val per_page: Int,
    val previous: Any,
    val total_pages: Int
)