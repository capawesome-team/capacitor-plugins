import Foundation

class FaroSession {

    let id: String
    let sampled: Bool
    var attributes: [String: String] = [:]

    init(id: String, sampled: Bool) {
        self.id = id
        self.sampled = sampled
    }

    static func create(samplingRate: Double) -> FaroSession {
        let sampled = Double.random(in: 0..<1) < samplingRate
        return FaroSession(id: UUID().uuidString, sampled: sampled)
    }

    static func create(id: String?, samplingRate: Double) -> FaroSession {
        let resolvedId = (id?.isEmpty == false) ? id! : UUID().uuidString
        let sampled = Double.random(in: 0..<1) < samplingRate
        return FaroSession(id: resolvedId, sampled: sampled)
    }
}
