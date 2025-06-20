import Foundation
import Libsql

@objc public class Libsql: NSObject {

    private var databases: [String: Database] = [:]
    private var connections: [String: Connection] = [:]
    private var transactions: [String: String] = [:]

    @objc public func connect(_ options: ConnectOptions, completion: @escaping (_ result: ConnectResult?, _ error: Error?) -> Void) throws {
        do {
            let connectionId = UUID().uuidString

            let database: Database

            if let url = options.url, let path = options.path, let authToken = options.authToken {
                // Embedded replica mode
                database = try Database(
                    path: path,
                    url: url,
                    authToken: authToken
                )
            } else if let url = options.url, let authToken = options.authToken {
                // Remote-only mode
                database = try Database(
                    url: url,
                    authToken: authToken
                )
            } else if let path = options.path {
                // Local-only mode
                database = try Database(path)
            } else {
                // In-memory database
                database = try Database(":memory:")
            }

            let connection = try database.connect()

            self.databases[connectionId] = database
            self.connections[connectionId] = connection

            let result = ConnectResult(connectionId: connectionId)
            completion(result, nil)
        } catch {
            completion(nil, error)
        }
    }

    @objc public func execute(_ options: ExecuteOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        do {
            guard let connection = self.connections[options.connectionId] else {
                completion(LibsqlError.connectionNotFound)
                return
            }

            if let values = options.values, !values.isEmpty {
                try connection.execute(options.statement, values as! [ValueRepresentable])
            } else {
                try connection.execute(options.statement)
            }

            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func executeBatch(_ options: ExecuteBatchOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        do {
            guard let connection = self.connections[options.connectionId] else {
                completion(LibsqlError.connectionNotFound)
                return
            }

            for (index, statement) in options.statement.enumerated() {
                let values = options.values?[safe: index]

                if let values = values, !values.isEmpty {
                    try connection.execute(statement, values as! [ValueRepresentable])
                } else {
                    try connection.execute(statement)
                }
            }

            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func query(_ options: QueryOptions, completion: @escaping (_ result: QueryResult?, _ error: Error?) -> Void) throws {
        do {
            guard let connection = self.connections[options.connectionId] else {
                completion(nil, LibsqlError.connectionNotFound)
                return
            }

            let rows: Rows

            if let values = options.values, !values.isEmpty {
                rows = try connection.query(options.statement, values as! [ValueRepresentable])
            } else {
                rows = try connection.query(options.statement)
            }

            let result = QueryResult(rows: rows)
            completion(result, nil)
        } catch {
            completion(nil, error)
        }
    }

    @objc public func beginTransaction(_ options: BeginTransactionOptions, completion: @escaping (_ result: BeginTransactionResult?, _ error: Error?) -> Void) throws {
        do {
            guard let connection = self.connections[options.connectionId] else {
                completion(nil, LibsqlError.connectionNotFound)
                return
            }

            _ = try connection.execute("BEGIN TRANSACTION")

            let transactionId = UUID().uuidString
            self.transactions[transactionId] = options.connectionId

            let result = BeginTransactionResult(transactionId: transactionId)
            completion(result, nil)
        } catch {
            completion(nil, error)
        }
    }

    @objc public func commitTransaction(_ options: CommitTransactionOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        do {
            guard let connection = self.connections[options.connectionId] else {
                completion(LibsqlError.connectionNotFound)
                return
            }

            guard self.transactions[options.transactionId] != nil else {
                completion(LibsqlError.transactionNotFound)
                return
            }

            _ = try connection.execute("COMMIT")
            self.transactions.removeValue(forKey: options.transactionId)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func rollbackTransaction(_ options: RollbackTransactionOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        do {
            guard let connection = self.connections[options.connectionId] else {
                completion(LibsqlError.connectionNotFound)
                return
            }

            guard self.transactions[options.transactionId] != nil else {
                completion(LibsqlError.transactionNotFound)
                return
            }

            _ = try connection.execute("ROLLBACK")
            self.transactions.removeValue(forKey: options.transactionId)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func sync(_ options: SyncOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        do {
            guard let database = self.databases[options.connectionId] else {
                completion(LibsqlError.databaseNotFound)
                return
            }

            try database.sync()
            completion(nil)
        } catch {
            completion(error)
        }
    }
}

extension Array {
    subscript(safe index: Index) -> Element? {
        return indices.contains(index) ? self[index] : nil
    }
}
