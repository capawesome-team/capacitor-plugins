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

    public func download(url: URL, destination: @escaping DownloadRequest.Destination, callback: ((Progress) -> Void)?) async -> AFDownloadResponse<Data> {
        var request = buildRequest(url: url, method: .get)
        setDeviceIdHeader(&request)
        return await download(request, destination: destination, callback: callback)
    }

    public func downloadWithoutDeviceId(url: URL, destination: @escaping DownloadRequest.Destination, callback: ((Progress) -> Void)?) async -> AFDownloadResponse<Data> {
        let request = buildRequest(url: url, method: .get)
        return await download(request, destination: destination, callback: callback)
    }

    /// Resolves for every status code, so that the caller can read the status, the headers and the body of an error answer.
    public func post<T: Encodable>(url: URL, body: T) async throws -> AFDataResponse<Data?> {
        var request = buildRequest(url: url, method: .post)
        setDeviceIdHeader(&request)
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = try JSONEncoder().encode(body)
        return await withCheckedContinuation { continuation in
            AF.request(request).response { response in
                continuation.resume(returning: response)
            }
        }
    }

    public func request<T: Decodable>(url: URL, type: T.Type) async throws -> AFDataResponse<T> {
        var request = buildRequest(url: url, method: .get)
        setDeviceIdHeader(&request)
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(request).validate().responseDecodable(of: type) { response in
                continuation.resume(returning: response)
            }
        }
    }

    private func buildRequest(url: URL, method: HTTPMethod) -> URLRequest {
        var request = URLRequest(url: url)
        // Ignore the URL cache so that a cached response is never replayed for a deterministic request URL
        request.cachePolicy = .reloadIgnoringLocalCacheData
        request.httpMethod = method.rawValue
        request.timeoutInterval = Double(config.httpTimeout) / 1000.0
        return request
    }

    private func download(_ request: URLRequest, destination: @escaping DownloadRequest.Destination, callback: ((Progress) -> Void)?) async -> AFDownloadResponse<Data> {
        return await withCheckedContinuation { continuation in
            // `validate()` treats non-2xx responses as errors so that an error body is never saved as a bundle file
            AF.download(request, to: destination).validate().downloadProgress { progress in
                if let callback = callback {
                    callback(progress)
                }
            }.responseData(emptyResponseCodes: [200, 204, 205]) { response in
                continuation.resume(returning: response)
            }
        }
    }

    private func setDeviceIdHeader(_ request: inout URLRequest) {
        if let deviceId = deviceId, !deviceId.isEmpty {
            request.setValue(deviceId, forHTTPHeaderField: "X-Capawesome-Device-Id")
        }
    }
}
