import Foundation
import Vapi

@objc public class VapiImpl: NSObject {
    var vapi: Vapi?
    
    @objc public func isMuted() async throws {
        guard let vapi = self.vapi else {
            throw CustomError.uninitialized
        }
        
        try await vapi.isMuted()
    }
    
    @objc public func send() async throws {
        guard let vapi = self.vapi else {
            throw CustomError.uninitialized
        }
        
        try await vapi.send(message: VapiMessage(type: "add-message", role: "system", content: <#T##String#>))
    }
    
    @objc public func setup(_ options: SetupOptions) {
        let apiKey = options.getApiKey()
        
        self.vapi = Vapi.init(publicKey: apiKey)
    }
}
