package com.slavikjunior.deorm.orm

import kotlin.math.exp

class QueryBuilder {

    private val query = StringBuilder()

    private val fromAndJoin = StringBuilder()
    private val selectedColumns = mutableListOf<String>()
    private var whereCondition = StringBuilder("where ")

    fun select(columns: List<String>): QueryBuilder {
        selectedColumns.addAll(columns)
        return this
    }

    fun from(table: String): QueryBuilder {
        fromAndJoin.append(table)
        return this
    }

    fun join(joinType: JoinType, rightTable: String, expression: String) {
        fromAndJoin.append(" ${joinType.name} $rightTable on $expression")
    }

    fun where(condition: String): QueryBuilder {
        whereCondition.append(condition)
        return this
    }

    fun orderBy(column: String, direction: String = "asc"): QueryBuilder {
        query.append(" order by ").append(column).append(" ").append(direction)
        return this
    }

    fun groupBy(column: String): QueryBuilder {
        query.append(" group by ").append(column)
        return this
    }

    fun having(expression: String): QueryBuilder {
        query.append(" having ").append(expression)
        return this
    }

    fun build(): String {
        val query = StringBuilder("select ")
        selectedColumns.forEachIndexed { index, column ->
            query.append(column)
            if (index < selectedColumns.size - 1)
                query.append(", ")
        }
        query.append(fromAndJoin)

        return query.toString()
    }

}

enum class JoinType {
    inner,
    left,
    right
}