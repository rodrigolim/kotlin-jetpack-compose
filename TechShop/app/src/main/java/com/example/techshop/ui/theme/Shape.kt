package com.example.techshop.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),     // ex.: chips, text fields
    medium = RoundedCornerShape(16.dp),   // ex.: Cards
    large = CutCornerShape(topStart = 24.dp), // ex.: bottom sheets, dialogs
    extraLarge = RoundedCornerShape(32.dp)
)