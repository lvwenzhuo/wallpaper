package com.yuren.base.extensions.resources

import java.util.Locale
import java.util.regex.Pattern

fun String?.hasContent(): Boolean = orEmpty().isNotBlank() && orEmpty().isNotEmpty()

fun String?.lower(locale: Locale = Locale.ROOT): String = orEmpty().lower(locale)

fun String?.upper(locale: Locale = Locale.ROOT): String = orEmpty().upper(locale)

fun CharSequence.isLink(): Boolean {
    val schemaMatcher = Pattern.compile("(https?://|mailto:).+").matcher("")
    return schemaMatcher.reset(this).matches()
}
