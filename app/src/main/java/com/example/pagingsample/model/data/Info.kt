package com.example.pagingsample.model.data

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String? = null
)