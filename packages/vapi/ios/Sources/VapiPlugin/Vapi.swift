import Foundation
import Combine
import Vapi

@objc public class VapiImpl: NSObject {
    private let plugin: VapiPlugin

    var subscription: AnyCancellable?
    var vapi: Vapi?

    init(plugin: VapiPlugin) {
        self.plugin = plugin
    }

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

        if self.subscription != nil {
            self.subscription?.cancel()
            self.subscription = nil
        }

        self.vapi = Vapi.init(publicKey: apiKey)
        self.subscription = self.vapi?.eventPublisher
            .sink { [weak self] event in
                switch event {
                case .callDidStart:
                    self?.callState = .started
                case .callDidEnd:
                    self?.callState = .ended
                case .speechUpdate:
                    print(event)
                case .conversationUpdate:
                    print(event)
                case .functionCall:
                    print(event)
                case .hang:
                    print(event)
                case .metadata:
                    print(event)
                case .transcript:
                    print(event)
                case .error(let error):
                    print("Error: \(error)")
                }
            }
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
