@file:JvmName(name = "Exceptions")

package com.slavikjunior.deorm.exceptions

import java.sql.SQLException

class DbAccessException(message: String) : SQLException(message) {
    constructor(message: String, cause: Throwable) : this( message + " " + cause.message + " " + cause.cause)
}

class DriverNotFoundException(message: String) : ClassNotFoundException(message)

class NotNullableColumnException(message: String) : SQLException(message)

class NotAnnotatedTableException(message: String) : SQLException(message)