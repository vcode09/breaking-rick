package com.vmoreno.butterfly.rick.utils

fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}