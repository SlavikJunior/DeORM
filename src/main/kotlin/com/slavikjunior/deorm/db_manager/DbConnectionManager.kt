package com.slavikjunior.deorm.db_manager

import com.slavikjunior.deorm.exceptions.DbAccessException
import com.slavikjunior.deorm.exceptions.DriverNotFoundException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties

object DbConnectionManager {
    private val properties = Properties()

    private lateinit var host: String
    private lateinit var port: String
    private lateinit var databaseName: String
    private lateinit var user: String
    private lateinit var password: String

    init {
        loadDriver()
        loadProperties()
        initialize()
    }

    fun configure(
        host: String = this.host,
        port: String = this.port,
        databaseName: String = this.databaseName,
        user: String = this.user,
        password: String = this.password
    ) {
        this.host = host
        this.port = port
        this.databaseName = databaseName
        this.user = user
        this.password = password
    }

    @Throws(DbAccessException::class)
    fun getConnection(): Connection {
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
        properties.load(this::class.java.getResourceAsStream("/application.properties"))
    }

    private fun initialize() {
        this.databaseName = properties.getProperty("database.name") ?: "postgres"
        this.host = properties.getProperty("host") ?: "localhost"
        this.port = properties.getProperty("port") ?: "5432"
        this.user = properties.getProperty("user") ?: "root"
        this.password = properties.getProperty("password") ?: "root"
    }
}