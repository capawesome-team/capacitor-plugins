package io.capawesome.capacitorjs.plugins.libsql

import io.capawesome.capacitorjs.plugins.libsql.classes.options.*
import io.capawesome.capacitorjs.plugins.libsql.classes.results.*
import io.capawesome.capacitorjs.plugins.libsql.interfaces.*
import tech.turso.libsql.Database
import tech.turso.libsql.EmbeddedReplicaDatabase
import java.io.File
import tech.turso.libsql.Connection as LibsqlConnection
import java.util.concurrent.ConcurrentHashMap
import java.util.UUID

class Libsql(private val plugin: LibsqlPlugin) {
    
    private val databases = ConcurrentHashMap<String, Database>()
    private val connections = ConcurrentHashMap<String, LibsqlConnection>()
    private val transactions = ConcurrentHashMap<String, String>()

    @Throws(Exception::class)
    fun connect(options: ConnectOptions, callback: NonEmptyResultCallback<ConnectResult>) {
        try {
            val connectionId = UUID.randomUUID().toString()
            
            val database = when {
                options.url != null && options.path != null -> {
                
                    tech.turso.libsql.Libsql.open(
                        url = options.url!!,
                        authToken = options.authToken!!,
                        path = resolvePath(options.path!!)
                    )
                }
                options.url != null -> {
                
                    tech.turso.libsql.Libsql.open(
                        url = options.url!!,
                        authToken = options.authToken!!
                    )
                }
                options.path != null -> {
                
                    tech.turso.libsql.Libsql.open(path = resolvePath(options.path!!))
                }
                else -> {
                    
                    tech.turso.libsql.Libsql.open(path = ":memory:")
                }
            }

            val connection = database.connect()
            
            databases[connectionId] = database
            connections[connectionId] = connection

            val result = ConnectResult(connectionId)
            callback.success(result)
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }

    @Throws(Exception::class)
    fun execute(options: ExecuteOptions, callback: EmptyCallback) {
        try {
            val connection = connections[options.connectionId]
                ?: throw Exception("Connection not found: ${options.connectionId}")

            val values = options.values?.toTypedArray()
            
            if (values != null && values.isNotEmpty()) {
                connection.execute(options.statement, *values)
            } else {
                connection.execute(options.statement)
            }

            callback.success()
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }

    @Throws(Exception::class)
    fun executeBatch(options: ExecuteBatchOptions, callback: EmptyCallback) {
        try {
            val connection = connections[options.connectionId]
                ?: throw Exception("Connection not found: ${options.connectionId}")

            for (i in options.statement.indices) {
                val statement = options.statement[i]
                val values = options.values?.getOrNull(i)?.toTypedArray()
                
                if (!values.isNullOrEmpty()) {
                    connection.execute(statement, *values)
                } else {
                    connection.execute(statement)
                }
            }

            callback.success()
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }

    @Throws(Exception::class)
    fun query(options: QueryOptions, callback: NonEmptyResultCallback<QueryResult>) {
        try {
            val connection = connections[options.connectionId]
                ?: throw Exception("Connection not found: ${options.connectionId}")

            val values = options.values?.toTypedArray()
            
            val rows = if (values != null && values.isNotEmpty()) {
                connection.query(options.statement, *values)
            } else {
                connection.query(options.statement)
            }

            val result = QueryResult(rows)
            callback.success(result)
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }

    @Throws(Exception::class)
    fun beginTransaction(options: BeginTransactionOptions, callback: NonEmptyResultCallback<BeginTransactionResult>) {
        try {
            val connection = connections[options.connectionId]
                ?: throw Exception("Connection not found: ${options.connectionId}")

            connection.execute("BEGIN TRANSACTION")
            
            val transactionId = UUID.randomUUID().toString()
            transactions[transactionId] = options.connectionId

            val result = BeginTransactionResult(transactionId)
            callback.success(result)
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }

    @Throws(Exception::class)
    fun commitTransaction(options: CommitTransactionOptions, callback: EmptyCallback) {
        try {
            val connection = connections[options.connectionId]
                ?: throw Exception("Connection not found: ${options.connectionId}")

            if (!transactions.containsKey(options.transactionId)) {
                throw Exception("Transaction not found: ${options.transactionId}")
            }

            connection.execute("COMMIT")
            transactions.remove(options.transactionId)

            callback.success()
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }

    @Throws(Exception::class)
    fun rollbackTransaction(options: RollbackTransactionOptions, callback: EmptyCallback) {
        try {
            val connection = connections[options.connectionId]
                ?: throw Exception("Connection not found: ${options.connectionId}")

            if (!transactions.containsKey(options.transactionId)) {
                throw Exception("Transaction not found: ${options.transactionId}")
            }

            connection.execute("ROLLBACK")
            transactions.remove(options.transactionId)

            callback.success()
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }

    
    @Throws(Exception::class)
    fun sync(options: SyncOptions, callback: EmptyCallback) {
        try {
            val database = databases[options.connectionId]
                ?: throw Exception("Database not found: ${options.connectionId}")

    
            val embeddedDb = database as? EmbeddedReplicaDatabase
                ?: throw Exception("Sync is only available for embedded replica databases")

            embeddedDb.sync()
            callback.success()
        } catch (exception: Exception) {
            callback.error(exception)
        }
    }
    

    private fun resolvePath(path: String): String {
        var file = File(path)
        if (!file.isAbsolute) {
            file = File(plugin.context.getDatabasePath(path).absolutePath)
        }
        return file.absolutePath
    }
}