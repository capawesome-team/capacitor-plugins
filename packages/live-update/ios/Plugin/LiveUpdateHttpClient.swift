import Foundation
import Capacitor
import Alamofire

public class LiveUpdateHttpClient: NSObject {

    private let config: LiveUpdateConfig
    private var deviceId: String?

    public static func getChecksumFromResponse(response: HTTPURLResponse) -> String? {
        guard let headers = response.allHeaderFields as? [String: String] else { return nil }
        return headers.first(where: { $0.key.lowercased() == "x-checksum" })?.value
    }

    public static func getSignatureFromResponse(response: HTTPURLResponse) -> String? {
        guard let headers = response.allHeaderFields as? [String: String] else { return nil }
        return headers.first(where: { $0.key.lowercased() == "x-signature" })?.value
    }

    init(config: LiveUpdateConfig) {
        self.config = config
    }

    public func setDeviceId(_ deviceId: String) {
        self.deviceId = deviceId
    }

    public func download(url: URL, destination: @escaping DownloadRequest.Destination, callback: ((Progress) -> Void)?) async throws -> AFDownloadResponse<Data> {
        var request = URLRequest(url: url)
        request.httpMethod = HTTPMethod.get.rawValue
        request.timeoutInterval = Double(config.httpTimeout) / 1000.0
        if let deviceId = deviceId {
            request.setValue(deviceId, forHTTPHeaderField: "X-Device-Id")
        }
        return try await withCheckedThrowingContinuation { continuation in
            AF.download(request, to: destination).downloadProgress { progress in
                if let callback = callback {
                    callback(progress)
                }
            }.responseData(emptyResponseCodes: [200, 204, 205]) { response in
                continuation.resume(returning: response)
            }
        }
    }

    public func request<T: Decodable>(url: URL, type: T.Type) async throws -> AFDataResponse<T> {
        var request = URLRequest(url: url)
        request.httpMethod = HTTPMethod.get.rawValue
        request.timeoutInterval = Double(config.httpTimeout) / 1000.0
        if let deviceId = deviceId {
            request.setValue(deviceId, forHTTPHeaderField: "X-Device-Id")
        }
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(request).validate().responseDecodable(of: type) { response in
                continuation.resume(returning: response)
            }
        }
    }
}
