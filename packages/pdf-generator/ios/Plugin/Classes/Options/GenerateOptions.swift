import Foundation
import Capacitor

@objc public class GenerateOptions: NSObject {
    let fileName: String
    let paperSize: CGSize
    let timeout: Int

    init(_ call: CAPPluginCall) throws {
        self.fileName = GenerateOptions.getFileNameFromCall(call)
        self.paperSize = try GenerateOptions.getPaperSizeFromCall(call)
        self.timeout = GenerateOptions.getTimeoutFromCall(call)
    }

    private static func getFileNameFromCall(_ call: CAPPluginCall) -> String {
        let fileName = call.getString("fileName") ?? UUID().uuidString + ".pdf"
        return fileName.hasSuffix(".pdf") ? fileName : fileName + ".pdf"
    }

    private static func getPaperSizeFromCall(_ call: CAPPluginCall) throws -> CGSize {
        let pageSize: CGSize
        switch call.getString("pageSize") ?? "A4" {
        case "A3":
            pageSize = CGSize(width: 842, height: 1191)
        case "A4":
            pageSize = CGSize(width: 595, height: 842)
        case "A5":
            pageSize = CGSize(width: 420, height: 595)
        case "LETTER":
            pageSize = CGSize(width: 612, height: 792)
        default:
            throw CustomError.pageSizeInvalid
        }
        switch call.getString("orientation") ?? "PORTRAIT" {
        case "LANDSCAPE":
            return CGSize(width: pageSize.height, height: pageSize.width)
        case "PORTRAIT":
            return pageSize
        default:
            throw CustomError.orientationInvalid
        }
    }

    private static func getTimeoutFromCall(_ call: CAPPluginCall) -> Int {
        return call.getInt("timeout") ?? 30000
    }
}
