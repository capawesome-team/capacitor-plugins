import Capacitor

@objc public class CopyFileOptions: NSObject {
    private let fromUrl: URL
    private var overwrite = true
    private let toUrl: URL

    init(_ call: CAPPluginCall) throws {
        guard let from = call.getString("from") else {
            throw CustomError.fromMissing
        }
        guard let fromUrl = URL(string: from) else {
            throw CustomError.invalidPath
        }
        self.fromUrl = fromUrl

        self.overwrite = call.getBool("overwrite", self.overwrite)

        guard let to = call.getString("to") else {
            throw CustomError.toMissing
        }
        guard let toUrl = URL(string: to) else {
            throw CustomError.invalidPath
        }
        self.toUrl = toUrl
    }

    func getFromUrl() -> URL {
        return fromUrl
    }

    func getOverwrite() -> Bool {
        return overwrite
    }

    func getToUrl() -> URL {
        return toUrl
    }
}
