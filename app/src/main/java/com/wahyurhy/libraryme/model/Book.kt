package com.wahyurhy.libraryme.model

import android.net.Uri

data class Book(
    var isbn: String,
    var judul: String,
    var terbit: String,
    var penerbit: String,
    var pengarang: String,
    var image: String,
)