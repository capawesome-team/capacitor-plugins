import Capacitor
import Foundation

@objc public class PushMeasurementOptions: NSObject {
    private let context: [String: String]?
    private let type: String
    private let values: [String: NSNumber]

    init(call: CAPPluginCall) throws {
        guard let type = call.getString("type"), !type.isEmpty else {
            throw CustomError.typeMissing
        }
        guard let valuesObject = call.getObject("values"), !valuesObject.isEmpty else {
            throw CustomError.valuesMissing
        }
        var numericValues: [String: NSNumber] = [:]
        for (key, raw) in valuesObject {
            if let number = raw as? NSNumber {
                numericValues[key] = number
            } else if let string = raw as? String, let parsed = Double(string) {
                numericValues[key] = NSNumber(value: parsed)
            }
        }
        guard !numericValues.isEmpty else {
            throw CustomError.valuesMissing
        }
        self.context = call.getObject("context") as? [String: String]
        self.type = type
        self.values = numericValues
    }

    func getContext() -> [String: String]? {
        return context
    }

    func getType() -> String {
        return type
    }

    func getValues() -> [String: NSNumber] {
        return values
    }
}
