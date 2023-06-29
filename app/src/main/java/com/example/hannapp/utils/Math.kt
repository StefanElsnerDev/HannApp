package com.example.hannapp.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.round() = BigDecimal(this).setScale(1, RoundingMode.HALF_UP).toDouble()
