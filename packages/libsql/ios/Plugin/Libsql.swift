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

            if let url = options.url, let path = options.getPath(), let authToken = options.authToken {
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
            } else if let path = options.getPath() {
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
                guard let valueRepresentables = values as? [ValueRepresentable] else {
                    completion(LibsqlError.invalidValues)
                    return
                }
                try connection.execute(options.statement, valueRepresentables)
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
                    guard let valueRepresentables = values as? [ValueRepresentable] else {
                        completion(LibsqlError.invalidValues)
                        return
                    }
                    try connection.execute(statement, valueRepresentables)
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
            let tableName = extractTableName(from: options.statement)

            guard let tableName = tableName else {
                throw LibsqlError.tableNameNotRecognized
            }

            let columns = try connection.query("PRAGMA table_info(\(tableName))")
            var columnNames: [String] = []

            while let column = columns.next() {
                let nameValue = try column.get(1)
                if case .text(let columnName) = nameValue {
                    columnNames.append(columnName)
                }
            }

            if let values = options.values, !values.isEmpty {
                guard let valueRepresentables = values as? [ValueRepresentable] else {
                    completion(nil, LibsqlError.invalidValues)
                    return
                }
                rows = try connection.query(options.statement, valueRepresentables)
            } else {
                rows = try connection.query(options.statement)
            }

            let result = QueryResult(rows: rows, columns: columnNames)
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

    private func extractTableName(from query: String) -> String? {
        let pattern = #"(?i)SELECT\s+\*\s+FROM\s+([a-zA-Z0-9_]+)"#

        if let regex = try? NSRegularExpression(pattern: pattern) {
            let range = NSRange(query.startIndex..., in: query)
            if let match = regex.firstMatch(in: query, range: range),
               let tableRange = Range(match.range(at: 1), in: query) {
                return String(query[tableRange])
            }
        }
        return nil
    }
}

extension Array {
    subscript(safe index: Index) -> Element? {
        return indices.contains(index) ? self[index] : nil
    }
}
