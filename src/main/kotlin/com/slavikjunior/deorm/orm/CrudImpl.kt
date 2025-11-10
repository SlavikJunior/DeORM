package com.slavikjunior.deorm.orm

import com.slavikjunior.deorm.dao.Dao
import com.slavikjunior.deorm.dao.UniversalDao
import com.slavikjunior.deorm.utils.toFieldMapByColumnNames
import java.sql.ResultSet

internal object CrudImpl : Crud {

    private val daoMap = mutableMapOf<Class<*>, UniversalDao<*>>()

    override fun <T : Entity> create(entity: T): T {
        return getDaoInstance(entity::class.java).createEntity(entity.toFieldMapByColumnNames())
    }

    override fun <T : Entity> getById(entityClass: Class<T>, id: Int) = getByValues(entityClass, mapOf("id" to id))

    override fun <T : Entity> getByValues(
        entityClass: Class<T>,
        columnsToValues: Map<String, Any?>
    ) = getDaoInstance(entityClass).readEntityByValues(columnsToValues) as List<T>?

    override fun <T : Entity> getUnique(entityClass: Class<T>, uniqueAttributes: Map<String, Any?>) =
        getByValues(entityClass, uniqueAttributes)?.first()

    override fun <T : Entity> update(entityClass: Class<T>, id: Int, columnsToValues: Map<String, Any?>) =
        getDaoInstance(entityClass).updateEntityByValues(id, columnsToValues)


    override fun <T : Entity> updateAndGet(entityClass: Class<T>, id: Int, columnsToValues: Map<String, Any?>) =
        if (update(entityClass, id, columnsToValues))
            getById(entityClass, id) as T?
        else null

    override fun <T : Entity> deleteByValues(entityClass: Class<T>, columnsToValues: Map<String, Any?>) =
        getDaoInstance(entityClass).deleteEntityByValues(columnsToValues)

    override fun <T : Entity> deleteById(entityClass: Class<T>, id: Int) =
        deleteByValues(entityClass, mapOf("id" to id))

    override fun <T : Entity> deleteByEntity(entity: T) =
        deleteByValues(entity::class.java, entity.toFieldMapByColumnNames())

    private fun <T : Entity> getDaoInstance(entityClass: Class<T>): Dao<T> {
        return daoMap.getOrPut(entityClass) { UniversalDao(entityClass) } as Dao<T>
    }

    override fun <T: Entity> executeQuery(entityClass: Class<T>, query: String): ResultSet {
        return getDaoInstance(entityClass).execute(query)
    }
}