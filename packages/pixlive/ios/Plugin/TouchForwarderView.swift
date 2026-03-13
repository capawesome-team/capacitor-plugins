import UIKit

class TouchForwarderView: UIView {

    var touchEnabled = false
    var touchHole: CGRect?
    weak var arView: UIView?

    override func point(inside point: CGPoint, with event: UIEvent?) -> Bool {
        guard touchEnabled, let arView = arView, arView.superview != nil, !arView.isHidden else {
            return true
        }
        let arPoint = convert(point, to: arView)
        if touchHole != nil && touchHole!.contains(arPoint) {
            return true
        }
        if arPoint.x >= 0 && arPoint.y >= 0
            && arPoint.x < arView.frame.size.width
            && arPoint.y < arView.frame.size.height {
            return false
        }
        return true
    }
}
