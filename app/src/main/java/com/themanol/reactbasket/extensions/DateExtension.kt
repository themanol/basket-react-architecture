package com.themanol.reactbasket.extensions

import java.text.SimpleDateFormat
import java.util.*

val simpleDateLocation = SimpleDateFormat("EEE, d MMM yyyy", Locale.ROOT)

fun Date.format(): String = simpleDateLocation.format(this)