package tech.codeabsolute.util

import kotlin.random.Random

fun String.Companion.empty() = ""

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun String.Companion.random() = (1..30)
    .map { Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")