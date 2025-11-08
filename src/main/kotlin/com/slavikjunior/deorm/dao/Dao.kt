package com.slavikjunior.deorm.dao

import com.slavikjunior.deorm.annotations.CreateMethod
import com.slavikjunior.deorm.annotations.DeleteMethod
import com.slavikjunior.deorm.annotations.ReadMethod
import com.slavikjunior.deorm.annotations.UpdateMethod
import com.slavikjunior.deorm.orm.Entity
import java.sql.ResultSet
import java.sql.SQLException

interface Dao<T: Entity> {
    @CreateMethod
    @Throws(SQLException::class)
    fun createEntity(columnsToValues: Map<String, Any?>): Boolean

    @ReadMethod
    @Throws(SQLException::class)
    fun readEntityByValues(columnsToValues: Map<String, Any?>): List<T>?

    @UpdateMethod
    @Throws(SQLException::class)
    fun updateEntityByValues(id: Int, columnsToValues: Map<String, Any?>): Boolean

    @DeleteMethod
    @Throws(SQLException::class)
    fun deleteEntityByValues(columnsToValues: Map<String, Any?>): Boolean

    fun getLastId(entityClass: Class<T>): Int?

    fun execute(query: String): ResultSet
}