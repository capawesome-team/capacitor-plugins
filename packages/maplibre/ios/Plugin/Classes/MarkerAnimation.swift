import Foundation
import QuartzCore

/// Interpolates the coordinates and the rotation of a marker over time.
public class MarkerAnimation: NSObject {
    private let duration: CFTimeInterval
    private let endCoordinates: LatLng
    private let onFinish: () -> Void
    private let onUpdate: (_ coordinates: LatLng, _ rotation: Double) -> Void
    private let rotationDelta: Double
    private let startCoordinates: LatLng
    private let startRotation: Double
    private var displayLink: CADisplayLink?
    private var startTime: CFTimeInterval = 0

    init(
        startCoordinates: LatLng,
        endCoordinates: LatLng,
        startRotation: Double,
        endRotation: Double,
        duration: CFTimeInterval,
        onUpdate: @escaping (_ coordinates: LatLng, _ rotation: Double) -> Void,
        onFinish: @escaping () -> Void
    ) {
        self.duration = duration
        self.endCoordinates = endCoordinates
        self.onFinish = onFinish
        self.onUpdate = onUpdate
        self.rotationDelta = MarkerAnimation.createRotationDelta(from: startRotation, to: endRotation)
        self.startCoordinates = startCoordinates
        self.startRotation = startRotation
    }

    func cancel() {
        displayLink?.invalidate()
        displayLink = nil
    }

    func start() {
        guard duration > 0 else {
            onUpdate(endCoordinates, startRotation + rotationDelta)
            onFinish()
            return
        }
        startTime = CACurrentMediaTime()
        let displayLink = CADisplayLink(target: self, selector: #selector(handleTick))
        displayLink.add(to: .main, forMode: .common)
        self.displayLink = displayLink
    }

    private static func createRotationDelta(from startRotation: Double, to endRotation: Double) -> Double {
        var delta = (endRotation - startRotation).truncatingRemainder(dividingBy: 360)
        if delta > 180 {
            delta -= 360
        } else if delta < -180 {
            delta += 360
        }
        return delta
    }

    @objc private func handleTick() {
        let progress = min((CACurrentMediaTime() - startTime) / duration, 1)
        let coordinates = LatLng(
            latitude: startCoordinates.latitude + (endCoordinates.latitude - startCoordinates.latitude) * progress,
            longitude: startCoordinates.longitude + (endCoordinates.longitude - startCoordinates.longitude) * progress
        )
        onUpdate(coordinates, startRotation + rotationDelta * progress)
        if progress >= 1 {
            cancel()
            onFinish()
        }
    }
}
