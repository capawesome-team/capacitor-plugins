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
    
    @objc public func say(_ options: SayOptions) async throws {
        guard let vapi = self.vapi else {
            throw CustomError.uninitialized
        }
        
        let message = options.getMessage()
        
        try await vapi.send(message: VapiMessage(type: "say", role: "system", content: message))
    }
    
    @objc public func setMuted(_ options: SetMutedOptions) async throws {
        guard let vapi = self.vapi else {
            throw CustomError.uninitialized
        }
        
        let muted = options.getMuted()
        
        try await vapi.setMuted(muted)
    }
    
    @objc public func setup(_ options: SetupOptions) {
        let apiKey = options.getApiKey()
        
        self.vapi = Vapi.init(publicKey: apiKey)
    }
    
    @objc public func start(_ options: StartOptions) async throws {
        guard let vapi = self.vapi else {
            throw CustomError.uninitialized
        }
        
        let assistantId = options.getAssistantId()
        
        try await vapi.start(assistantId: assistantId)
    }
    
    @objc public func stop() async throws {
        guard let vapi = self.vapi else {
            throw CustomError.uninitialized
        }
        
        try await vapi.stop()
    }
}
