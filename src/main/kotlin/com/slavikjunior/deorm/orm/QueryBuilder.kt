package com.slavikjunior.deorm.orm

class QueryBuilder() {

    private val query = StringBuilder("select " )

    fun select(vararg columns: String): QueryBuilder {
        columns.forEachIndexed { index, column ->
            query.append(column)
            if(index < columns.size - 1)
                query.append(", ")
        }
        return this
    }

    fun from(table: String): QueryBuilder {
        query.append(" from ").append(table)
        return this
    }

    fun where(condition: String): QueryBuilder {
        query.append(" where ").append(condition)
        return this
    }

    fun and(condition: String): QueryBuilder {
        query.append(" and ").append(condition)
        return this
    }

    fun orderBy(column: String, direction: String = "asc"): QueryBuilder {
        query.append(" order by ").append(column).append(" ").append(direction)
        return this
    }

    fun groupBy(vararg columns: String): QueryBuilder {
        columns.forEachIndexed { index, column ->
            query.append(column)
            if(index < columns.size - 1)
                query.append(", ")
        }
        return this
    }

    fun execute(): String {
        return query.toString()
    }
}

enum class jointype {
    inner,
    left,
    right
}