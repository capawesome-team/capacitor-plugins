import Foundation

struct PreviousCrash {
    let type: String
    let value: String
    let frames: [[String: Any]]
    let timestamp: Date
}
