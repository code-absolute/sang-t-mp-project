package tech.codeabsolute.util

import kotlin.random.Random

fun Int.Companion.random() = Random.nextInt(0, MAX_VALUE)