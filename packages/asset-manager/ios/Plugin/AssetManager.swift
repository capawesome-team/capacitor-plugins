import Foundation

@objc public class AssetManager: NSObject {
    private let plugin: AssetManagerPlugin

    init(plugin: AssetManagerPlugin) {
        self.plugin = plugin
        super.init()
    }

    @objc public func copy(_ options: CopyOptions, completion: @escaping (Error?) -> Void) throws {
        guard let fromURL = Bundle.main.url(forResource: options.from, withExtension: nil) else {
            return
        }
        guard let toURL = URL.init(string: options.to) else {
            return
        }

        try FileManager.default.copyItem(at: fromURL, to: toURL)
        completion(nil)
    }

    @objc public func list(_ options: ListOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let bundle = Bundle.main
        let paths = bundle.paths(forResourcesOfType: nil, inDirectory: options.path)
        let result = ListResult(files: paths)
        completion(result, nil)
    }

    @objc public func read(_ options: ReadOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        guard let url = Bundle.main.resourceURL?.appendingPathComponent(options.path) else {
            return
        }
        var result: ReadResult?
        if options.encoding == "utf8" {
            let data = try String(contentsOf: url, encoding: .utf8)
            result = ReadResult(data: data)
        } else {
            let data = try Data(contentsOf: url)
            result = ReadResult(data: data.base64EncodedString())
        }
        completion(result, nil)
    }
}
