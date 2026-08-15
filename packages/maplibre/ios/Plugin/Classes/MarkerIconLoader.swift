import Foundation
import UIKit

/// Loads and caches the icons of markers and assigns the name each icon is
/// registered with in the style of a map.
public class MarkerIconLoader {
    static let defaultIconName = "capawesome-maplibre-default-icon"

    private var iconNamesByCacheKey = [String: String]()
    private var imagesByIconName = [String: UIImage]()
    private var nextIconIndex = 0

    func image(forName name: String) -> UIImage? {
        return imagesByIconName[name]
    }

    func loadIcons(for markers: [Marker], completion: @escaping (_ error: Error?) -> Void) {
        imagesByIconName[Self.defaultIconName] = imagesByIconName[Self.defaultIconName] ?? MapLibreHelper.createDefaultMarkerIcon()
        let group = DispatchGroup()
        var iconNamesByPendingCacheKey = [String: String]()
        var loadError: Error?
        for marker in markers {
            guard let iconUrl = marker.iconUrl else {
                marker.iconName = Self.defaultIconName
                continue
            }
            let cacheKey = Self.createCacheKey(iconUrl, marker.iconSize)
            if let iconName = iconNamesByCacheKey[cacheKey] ?? iconNamesByPendingCacheKey[cacheKey] {
                marker.iconName = iconName
                continue
            }
            let iconName = createIconName()
            iconNamesByPendingCacheKey[cacheKey] = iconName
            marker.iconName = iconName
            group.enter()
            loadImage(iconUrl, size: marker.iconSize) { [weak self] image in
                if let image = image {
                    self?.imagesByIconName[iconName] = image
                    self?.iconNamesByCacheKey[cacheKey] = iconName
                } else {
                    loadError = CustomError.iconLoadFailed
                }
                group.leave()
            }
        }
        group.notify(queue: .main) {
            completion(loadError)
        }
    }

    private static func createCacheKey(_ iconUrl: String, _ size: CGSize?) -> String {
        return "\(iconUrl)|\(size?.width ?? 0)x\(size?.height ?? 0)"
    }

    private static func resizeImage(_ image: UIImage, to size: CGSize) -> UIImage {
        return UIGraphicsImageRenderer(size: size).image { _ in
            image.draw(in: CGRect(origin: .zero, size: size))
        }
    }

    private func createIconName() -> String {
        nextIconIndex += 1
        return "capawesome-maplibre-icon-\(nextIconIndex)"
    }

    private func loadImage(_ iconUrl: String, size: CGSize?, completion: @escaping (_ image: UIImage?) -> Void) {
        guard let url = URL(string: iconUrl) else {
            completion(nil)
            return
        }
        let handleData: (Data?) -> Void = { data in
            var image = data.flatMap { UIImage(data: $0) }
            if let size = size, let loadedImage = image {
                image = Self.resizeImage(loadedImage, to: size)
            }
            DispatchQueue.main.async {
                completion(image)
            }
        }
        guard url.scheme == "https" else {
            DispatchQueue.global(qos: .userInitiated).async {
                handleData(try? Data(contentsOf: url))
            }
            return
        }
        URLSession.shared.dataTask(with: url) { data, _, _ in
            handleData(data)
        }
        .resume()
    }
}
