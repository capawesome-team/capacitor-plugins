import FerrostarCore
import FerrostarCoreFFI
import Foundation

class EmptyRouteProvider: CustomRouteProvider {
    func getRoutes(userLocation: UserLocation, waypoints: [Waypoint]) async throws -> [Route] {
        return []
    }
}
