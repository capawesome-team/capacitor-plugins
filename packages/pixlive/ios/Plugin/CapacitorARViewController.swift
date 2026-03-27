import Foundation
import VDARSDK

class CapacitorARViewController: VDARLiveAnnotationViewController {

    weak var pixlive: Pixlive?

    override func annotationViewDidBecomeEmpty() {
        super.annotationViewDidBecomeEmpty()
        pixlive?.annotationViewDidBecomeEmpty()
    }

    override func annotationViewDidPresentAnnotations() {
        super.annotationViewDidPresentAnnotations()
        pixlive?.annotationViewDidPresentAnnotations()
    }
}
