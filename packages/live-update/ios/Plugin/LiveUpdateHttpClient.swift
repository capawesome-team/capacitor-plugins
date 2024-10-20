import Foundation
import Capacitor
import Alamofire

public class LiveUpdateHttpClient: NSObject {

    private let config: LiveUpdateConfig

    public static func getChecksumFromResponse(response: HTTPURLResponse) -> String? {
        return response.allHeaderFields["x-checksum"] as? String // Must be lowercase!
    }

    public static func getSignatureFromResponse(response: HTTPURLResponse) -> String? {
        return response.allHeaderFields["x-signature"] as? String // Must be lowercase!
    }

    init(config: LiveUpdateConfig) {
        self.config = config
    }

    public func download(url: URL, destination: @escaping DownloadRequest.Destination) async throws -> AFDownloadResponse<Data> {
        var request = URLRequest(url: url)
        request.httpMethod = HTTPMethod.get.rawValue
        request.timeoutInterval = Double(config.httpTimeout) / 1000.0
        return try await withCheckedThrowingContinuation { continuation in
            AF.download(request, to: destination).responseData { response in
                continuation.resume(returning: response)
            }
        }
    }

    public func request<T: Decodable>(url: URL, type: T.Type) async throws -> AFDataResponse<T> {
        var request = URLRequest(url: url)
        request.httpMethod = HTTPMethod.get.rawValue
        request.timeoutInterval = Double(config.httpTimeout) / 1000.0
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(request).validate().responseDecodable(of: type) { response in
                continuation.resume(returning: response)
            }
        }
    }
}
