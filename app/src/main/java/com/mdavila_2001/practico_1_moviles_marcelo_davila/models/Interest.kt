package com.mdavila_2001.practico_1_moviles_marcelo_davila.models

class Interest(
    var id: Int,
    var name: String,
    var description: String,
    var images: ArrayList<String>,
    var liked: Boolean = false,
    var disliked: Boolean = false,
)