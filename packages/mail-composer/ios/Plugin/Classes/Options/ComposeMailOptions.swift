import Foundation
import Capacitor

@objc public class ComposeMailOptions: NSObject {
    let attachments: [String]
    let bccRecipients: [String]
    let body: String?
    let ccRecipients: [String]
    let isHtml: Bool
    let subject: String?
    let toRecipients: [String]

    init(_ call: CAPPluginCall) {
        self.toRecipients = call.getArray("to", String.self) ?? []
        self.ccRecipients = call.getArray("cc", String.self) ?? []
        self.bccRecipients = call.getArray("bcc", String.self) ?? []
        self.subject = call.getString("subject")
        self.body = call.getString("body")
        self.isHtml = call.getBool("isHtml", false)
        self.attachments = call.getArray("attachments", String.self) ?? []
    }
}
