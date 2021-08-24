package com.phantomlabs.firequiz.Activity.utils

object ColorPicker {
    val colors = arrayOf(
        "#ebae34",
        "#eb7a34",
        "#ebae34",
        "#eb7134",
        "#eb3434",
        "#34ebeb",
        "#3489eb",
        "#9234eb"
    )
    var currentColorIndex = 0
    fun getColor() : String {
        currentColorIndex = (currentColorIndex + 1) % colors.size
        return colors[currentColorIndex]
    }
}