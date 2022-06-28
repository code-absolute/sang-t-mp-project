package tech.codeabsolute.model

import org.joda.time.DateTime

data class DateOfBirth(
    val day: Int = -1,
    val month: Int = -1,
    val year: Int = -1
) {
    fun toDateTime(): DateTime {
        return DateTime(year, month, day, 0, 0)
    }
}

fun String.toDateOfBirth(): DateOfBirth {

    val date = replace("/", "")
    val year = date.substring(0, 2).toInt()
    val month = date.substring(2, 4).toInt()
    val day = date.substring(4, 6).toInt()

    return DateOfBirth(year = year, month = month, day = day)
}