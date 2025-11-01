package com.slavikjunior.deorm.db_manager

import com.slavikjunior.deorm.exceptions.DbAccessException
import com.slavikjunior.deorm.exceptions.DriverNotFoundException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties

object DbConnectionManager {
    private val properties = Properties()

    private var host: String = "localhost"
    private var port: String = "5432"
    private var databaseName: String = "postgres"
    private var user: String = "postgres"
    private var password: String = "password"

    fun initialize() {
        loadDriver()
        loadProperties()
        applyProperties()
    }

    fun configure(
        host: String,
        port: String,
        databaseName: String,
        user: String,
        password: String
    ) {
        this.host = host
        this.port = port
        this.databaseName = databaseName
        this.user = user
        this.password = password
    }

    @Throws(DbAccessException::class)
    fun getConnection(): Connection {
        // Гарантируем, что драйвер загружен
        loadDriver()

        try {
            val url = "jdbc:postgresql://$host:$port/$databaseName"
            val conn = DriverManager.getConnection(url, user, password)
            return conn
        } catch (e: SQLException) {
            throw DbAccessException("Failed to connect to DB.", e)
        }
    }

    private fun loadDriver() {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            throw DriverNotFoundException("PostgreSQL JDBC driver not found.")
        }
    }

    private fun loadProperties() {
        try {
            properties.load(this::class.java.getResourceAsStream("/application.properties"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun applyProperties() {
        properties.getProperty("database.name")?.let { databaseName = it }
        properties.getProperty("host")?.let { host = it }
        properties.getProperty("port")?.let { port = it }
        properties.getProperty("user")?.let { user = it }
        properties.getProperty("password")?.let { password = it }
    }
}