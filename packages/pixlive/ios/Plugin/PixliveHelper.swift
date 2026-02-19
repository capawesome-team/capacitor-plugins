import Foundation
import Capacitor
import VDARSDK

public class PixliveHelper {

    static func buildTagPriors(_ tags: [[String]]) -> [VDARPrior] {
        var priors: [VDARPrior] = []
        for tagGroup in tags {
            var innerPriors: [VDARPrior] = []
            for tag in tagGroup {
                innerPriors.append(VDARTagPrior(tagName: tag))
            }
            priors.append(VDARIntersectionPrior(priors: innerPriors))
        }
        return priors
    }

    static func buildFullPriors(tags: [[String]], tourIds: [Int], contextIds: [String]) -> [VDARPrior] {
        var priors = buildTagPriors(tags)
        for tourId in tourIds {
            priors.append(VDARTourPrior.tour(withID: Int32(tourId)))
        }
        for contextId in contextIds {
            priors.append(VDARContextPrior(contextID: contextId))
        }
        return priors
    }

    static func contextToJSObject(_ context: VDARContext) -> JSObject {
        var obj = JSObject()
        obj["contextId"] = context.remoteID ?? ""
        obj["name"] = context.name ?? ""
        obj["description"] = context.contextDescription != nil ? context.contextDescription : NSNull()
        if let lastmodif = context.lastmodif {
            obj["lastUpdate"] = "\(lastmodif)"
        } else {
            obj["lastUpdate"] = ""
        }
        obj["imageThumbnailURL"] = context.imageThumbnailURL?.absoluteString ?? NSNull()
        obj["imageHiResURL"] = context.imageHiResURL?.absoluteString ?? NSNull()
        obj["notificationTitle"] = context.notificationTitle != nil ? context.notificationTitle : NSNull()
        obj["notificationMessage"] = context.notificationMessage != nil ? context.notificationMessage : NSNull()
        obj["tags"] = JSArray()
        return obj
    }

    static func gpsPointToJSObject(_ point: VDARGPSPoint) -> JSObject {
        var obj = JSObject()
        obj["contextId"] = point.contextId ?? ""
        obj["category"] = point.category ?? ""
        obj["label"] = point.label ?? ""
        obj["latitude"] = point.lat
        obj["longitude"] = point.lon
        obj["detectionRadius"] = point.detectionRadius > 0 ? point.detectionRadius : NSNull()
        obj["distanceFromCurrentPosition"] = point.distanceFromCurrentPos
        return obj
    }

    static func codeTypeToString(_ codeType: VDARCodeType) -> String {
        if codeType.rawValue == VDAR_CODE_TYPE_QRCODE.rawValue {
            return "qrcode"
        } else if codeType.rawValue == VDAR_CODE_TYPE_EAN8.rawValue {
            return "ean8"
        } else if codeType.rawValue == VDAR_CODE_TYPE_EAN13.rawValue {
            return "ean13"
        } else if codeType.rawValue == VDAR_CODE_TYPE_UPCA.rawValue {
            return "upca"
        } else if codeType.rawValue == VDAR_CODE_TYPE_UPCE.rawValue {
            return "upce"
        } else if codeType.rawValue == VDAR_CODE_TYPE_ISBN13.rawValue {
            return "isbn13"
        } else if codeType.rawValue == VDAR_CODE_TYPE_CODE39.rawValue {
            return "code39"
        } else if codeType.rawValue == VDAR_CODE_TYPE_I25.rawValue {
            return "itf"
        } else {
            return "unknown"
        }
    }
}
