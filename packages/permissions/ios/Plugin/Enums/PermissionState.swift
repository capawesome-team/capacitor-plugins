import Foundation

public enum PermissionState: String {
    case denied
    case granted
    case limited
    case prompt
    case promptWithRationale = "prompt-with-rationale"
    case unavailable
}
