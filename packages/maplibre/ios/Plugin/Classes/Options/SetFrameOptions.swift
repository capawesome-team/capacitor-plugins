import Capacitor
import Foundation

@objc public class SetFrameOptions: NSObject {
    let contentSize: MapContentSize?
    let frame: MapFrame
    let mapId: String

    init(_ call: CAPPluginCall) throws {
        guard let frame = MapFrame.fromJSObject(call.getObject("frame")) else {
            throw CustomError.frameMissing
        }
        self.contentSize = MapContentSize.fromJSObject(call.getObject("contentSize"))
        self.frame = frame
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
    }
}
