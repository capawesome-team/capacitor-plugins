import Foundation

enum LibsqlError: Error {
    case connectionIdMissing
    case statementMissing
    case transactionIdMissing
    case connectionNotFound
    case databaseNotFound
    case transactionNotFound
    case invalidStatement
    case invalidValues

    var code: String? {
        switch self {
        case .connectionIdMissing:
            return nil
        case .statementMissing:
            return nil
        case .transactionIdMissing:
            return nil
        case .connectionNotFound:
            return nil
        case .databaseNotFound:
            return nil
        case .transactionNotFound:
            return nil
        case .invalidStatement:
            return nil
        case .invalidValues:
            return nil
        }
    }
}

extension LibsqlError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .connectionIdMissing:
            return NSLocalizedString("connectionId must be provided.", comment: "connectionIdMissing")
        case .statementMissing:
            return NSLocalizedString("statement must be provided.", comment: "statementMissing")
        case .transactionIdMissing:
            return NSLocalizedString("transactionId must be provided.", comment: "transactionIdMissing")
        case .connectionNotFound:
            return NSLocalizedString("Connection not found.", comment: "connectionNotFound")
        case .databaseNotFound:
            return NSLocalizedString("Database not found.", comment: "databaseNotFound")
        case .transactionNotFound:
            return NSLocalizedString("Transaction not found.", comment: "transactionNotFound")
        case .invalidStatement:
            return NSLocalizedString("All statements must be strings.", comment: "invalidStatement")
        case .invalidValues:
            return NSLocalizedString("All values must be arrays.", comment: "invalidValues")
        }
    }
}
