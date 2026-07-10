import Foundation
import FormbricksSDK

@objc public class Formbricks: NSObject {
    @objc public func logout() {
        FormbricksSDK.Formbricks.logout()
    }

    @objc public func setAttribute(_ options: SetAttributeOptions) {
        FormbricksSDK.Formbricks.setAttribute(options.getValue(), forKey: options.getKey())
    }

    @objc public func setAttributes(_ options: SetAttributesOptions) {
        let attributes = options.getAttributes().mapValues { AttributeValue.string($0) }
        FormbricksSDK.Formbricks.setAttributes(attributes)
    }

    @objc public func setLanguage(_ options: SetLanguageOptions) {
        FormbricksSDK.Formbricks.setLanguage(options.getLanguage())
    }

    @objc public func setUserId(_ options: SetUserIdOptions) {
        FormbricksSDK.Formbricks.setUserId(options.getUserId())
    }

    @objc public func setup(_ options: SetupOptions) {
        let config = FormbricksConfig.Builder(appUrl: options.getAppUrl(), environmentId: options.getEnvironmentId())
            .build()
        FormbricksSDK.Formbricks.setup(with: config)
    }

    @objc public func track(_ options: TrackOptions) {
        FormbricksSDK.Formbricks.track(options.getAction())
    }
}
