package com.phantomlabs.firequiz.Activity.models

data class Quiz(
    var id:String = "",
    var title:String = "",
    var question:MutableMap<String,Questions> = mutableMapOf()
)