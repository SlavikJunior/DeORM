package com.slavikjunior.deorm

import com.slavikjunior.deorm.orm.QueryBuilder

fun main() {

    val query = QueryBuilder()
        .select(listOf("name", "surname", "phone"))

}
