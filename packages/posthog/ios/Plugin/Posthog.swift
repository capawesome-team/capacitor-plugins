import Foundation
import PostHog

@objc public class Posthog: NSObject {
    private let plugin: PosthogPlugin

    init(plugin: PosthogPlugin) {
        self.plugin = plugin
    }

    @objc public func alias(_ options: AliasOptions) {
        let alias = options.getAlias()

        PostHogSDK.shared.alias(alias)
    }

    @objc public func capture(_ options: CaptureOptions) {
        let event = options.getEvent()
        let properties = options.getProperties()

        PostHogSDK.shared.capture(event, properties: properties)
    }

    @objc public func flush() {
        PostHogSDK.shared.flush()
    }

    @objc public func group(_ options: GroupOptions) {
        let type = options.getType()
        let key = options.getKey()
        let groupProperties = options.getGroupProperties()

        PostHogSDK.shared.group(type: type, key: key, groupProperties: groupProperties)
    }

    @objc public func identify(_ options: IdentifyOptions) {
        let distinctId = options.getDistinctId()
        let userProperties = options.getUserProperties()

        PostHogSDK.shared.identify(distinctId, userProperties: userProperties)
    }

    @objc public func register(_ options: RegisterOptions) {
        let key = options.getKey()
        let value = options.getValue()

        let properties = [key: value]

        PostHogSDK.shared.register(properties)
    }

    @objc public func reset() {
        PostHogSDK.shared.reset()
    }

    @objc public func screen(_ options: ScreenOptions) {
        let screenTitle = options.getScreenTitle()
        let properties = options.getProperties()

        PostHogSDK.shared.screen(screenTitle, properties: properties)
    }

    @objc public func setup(_ options: SetupOptions) {
        let apiKey = options.getApiKey()
        let host = options.getHost()

        let config = PostHogConfig(apiKey: apiKey, host: host)

        PostHogSDK.shared.setup(config)
    }

    @objc public func unregister(_ options: UnregisterOptions) {
        let key = options.getKey()

        PostHogSDK.shared.unregister(key)
    }
}
