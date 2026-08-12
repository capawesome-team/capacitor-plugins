import Foundation
import CoreGraphics
import ImageIO
import UniformTypeIdentifiers

@objc public class PhotoManipulator: NSObject {
    private let imagesDirectoryName = "capawesome_capacitor_photo_manipulator_images"
    private let plugin: PhotoManipulatorPlugin
    private let queue = DispatchQueue(label: "io.capawesome.capacitorjs.plugins.photomanipulator", qos: .userInitiated)

    init(plugin: PhotoManipulatorPlugin) {
        self.plugin = plugin
        super.init()
        queue.async {
            self.cleanUpImagesDirectory()
        }
    }

    @objc public func getInfo(_ options: GetInfoOptions, completion: @escaping (GetInfoResult?, Error?) -> Void) {
        queue.async {
            do {
                completion(try self.fetchInfo(options), nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func transform(_ options: TransformOptions, completion: @escaping (TransformResult?, Error?) -> Void) {
        queue.async {
            do {
                completion(try self.transformImage(options), nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    private func applyTransformations(
        _ image: CGImage,
        targetWidth: Int,
        targetHeight: Int,
        options: TransformOptions
    ) throws -> CGImage {
        let swapDimensions = options.rotate == 90 || options.rotate == 270
        let canvasWidth = swapDimensions ? targetHeight : targetWidth
        let canvasHeight = swapDimensions ? targetWidth : targetHeight
        guard let context = CGContext(
            data: nil,
            width: canvasWidth,
            height: canvasHeight,
            bitsPerComponent: 8,
            bytesPerRow: 0,
            space: CGColorSpaceCreateDeviceRGB(),
            bitmapInfo: CGImageAlphaInfo.premultipliedLast.rawValue
        ) else {
            throw CustomError.transformFailed
        }
        context.interpolationQuality = .high
        context.translateBy(x: CGFloat(canvasWidth) / 2, y: CGFloat(canvasHeight) / 2)
        if options.flipHorizontal {
            context.scaleBy(x: -1, y: 1)
        }
        if options.flipVertical {
            context.scaleBy(x: 1, y: -1)
        }
        context.rotate(by: CGFloat(-options.rotate) * .pi / 180)
        context.draw(
            image,
            in: CGRect(
                x: -CGFloat(targetWidth) / 2,
                y: -CGFloat(targetHeight) / 2,
                width: CGFloat(targetWidth),
                height: CGFloat(targetHeight)
            )
        )
        guard let result = context.makeImage() else {
            throw CustomError.transformFailed
        }
        return result
    }

    private func calculateMaxPixelSize(sourceWidth: Int, sourceHeight: Int, cropWidth: Int, cropHeight: Int, targetSize: CGSize?) -> Int {
        let maxSourceDimension = max(sourceWidth, sourceHeight)
        guard let targetSize = targetSize else {
            return maxSourceDimension
        }
        let scale = min(1, max(Double(targetSize.width) / Double(cropWidth), Double(targetSize.height) / Double(cropHeight)))
        return max(1, Int((Double(maxSourceDimension) * scale).rounded(.up)))
    }

    private func cleanUpImagesDirectory() {
        guard let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else {
            return
        }
        let directory = cachesDirectory.appendingPathComponent(imagesDirectoryName)
        try? FileManager.default.removeItem(at: directory)
    }

    private func createImageSource(_ url: URL) throws -> CGImageSource {
        let options = [kCGImageSourceShouldCache: false] as CFDictionary
        guard let source = CGImageSourceCreateWithURL(url as CFURL, options) else {
            throw CustomError.unsupportedFormat
        }
        return source
    }

    private func decodeImage(_ source: CGImageSource, maxPixelSize: Int) throws -> CGImage {
        let options = [
            kCGImageSourceCreateThumbnailFromImageAlways: true,
            kCGImageSourceCreateThumbnailWithTransform: true,
            kCGImageSourceShouldCacheImmediately: true,
            kCGImageSourceThumbnailMaxPixelSize: maxPixelSize
        ] as [CFString: Any] as CFDictionary
        guard let image = CGImageSourceCreateThumbnailAtIndex(source, 0, options) else {
            throw CustomError.unsupportedFormat
        }
        return image
    }

    private func encodeImage(_ image: CGImage, format: String, quality: Double) throws -> URL {
        let type: UTType
        let fileExtension: String
        switch format {
        case TransformOptions.formatPng:
            type = .png
            fileExtension = "png"
        case TransformOptions.formatWebp:
            throw CustomError.unsupportedFormat
        default:
            type = .jpeg
            fileExtension = "jpeg"
        }
        let url = try getImagesDirectory().appendingPathComponent(UUID().uuidString + "." + fileExtension)
        guard let destination = CGImageDestinationCreateWithURL(url as CFURL, type.identifier as CFString, 1, nil) else {
            throw CustomError.transformFailed
        }
        let properties = [kCGImageDestinationLossyCompressionQuality: quality] as CFDictionary
        CGImageDestinationAddImage(destination, image, properties)
        guard CGImageDestinationFinalize(destination) else {
            throw CustomError.transformFailed
        }
        return url
    }

    private func fetchInfo(_ options: GetInfoOptions) throws -> GetInfoResult {
        let url = try getFileUrl(options.path)
        let source = try createImageSource(url)
        let sourceSize = try getOrientedSize(source)
        let format = getFormatFromType(CGImageSourceGetType(source) as String?)
        return GetInfoResult(format: format, height: Int(sourceSize.height), width: Int(sourceSize.width))
    }

    private func getFileUrl(_ path: String) throws -> URL {
        let url: URL
        if path.hasPrefix("file:") {
            guard let fileUrl = URL(string: path) else {
                throw CustomError.pathInvalid
            }
            url = fileUrl
        } else if path.hasPrefix("/") {
            url = URL(fileURLWithPath: path)
        } else {
            throw CustomError.pathInvalid
        }
        guard FileManager.default.fileExists(atPath: url.path) else {
            throw CustomError.fileNotFound
        }
        return url
    }

    private func getFormatFromType(_ type: String?) -> String? {
        guard let type = type, let format = UTType(type)?.preferredFilenameExtension?.lowercased() else {
            return nil
        }
        return format == "jpg" ? "jpeg" : format
    }

    private func getImagesDirectory() throws -> URL {
        guard let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else {
            throw CustomError.transformFailed
        }
        let directory = cachesDirectory.appendingPathComponent(imagesDirectoryName)
        try FileManager.default.createDirectory(at: directory, withIntermediateDirectories: true)
        return directory
    }

    private func getOrientedSize(_ source: CGImageSource) throws -> CGSize {
        guard let properties = CGImageSourceCopyPropertiesAtIndex(source, 0, nil) as? [CFString: Any],
              let width = (properties[kCGImagePropertyPixelWidth] as? NSNumber)?.intValue,
              let height = (properties[kCGImagePropertyPixelHeight] as? NSNumber)?.intValue else {
            throw CustomError.unsupportedFormat
        }
        let orientation = (properties[kCGImagePropertyOrientation] as? NSNumber)?.intValue ?? 1
        if orientation >= 5 {
            return CGSize(width: height, height: width)
        }
        return CGSize(width: width, height: height)
    }

    private func getTargetSize(cropWidth: Int, cropHeight: Int, resizeWidth: Int?, resizeHeight: Int?) -> CGSize? {
        if resizeWidth == nil && resizeHeight == nil {
            return nil
        }
        if let resizeWidth = resizeWidth, let resizeHeight = resizeHeight {
            return CGSize(width: resizeWidth, height: resizeHeight)
        }
        if let resizeWidth = resizeWidth {
            let height = max(1, Int((Double(cropHeight) * Double(resizeWidth) / Double(cropWidth)).rounded()))
            return CGSize(width: resizeWidth, height: height)
        }
        guard let resizeHeight = resizeHeight else {
            return nil
        }
        let width = max(1, Int((Double(cropWidth) * Double(resizeHeight) / Double(cropHeight)).rounded()))
        return CGSize(width: width, height: resizeHeight)
    }

    private func transformImage(_ options: TransformOptions) throws -> TransformResult {
        let url = try getFileUrl(options.path)
        let source = try createImageSource(url)
        let sourceSize = try getOrientedSize(source)
        var crop = CGRect(origin: .zero, size: sourceSize)
        if let customCrop = options.crop {
            guard customCrop.maxX <= sourceSize.width, customCrop.maxY <= sourceSize.height else {
                throw CustomError.cropInvalid
            }
            crop = customCrop
        }
        let targetSize = getTargetSize(
            cropWidth: Int(crop.width),
            cropHeight: Int(crop.height),
            resizeWidth: options.resizeWidth,
            resizeHeight: options.resizeHeight
        )
        let maxPixelSize = calculateMaxPixelSize(
            sourceWidth: Int(sourceSize.width),
            sourceHeight: Int(sourceSize.height),
            cropWidth: Int(crop.width),
            cropHeight: Int(crop.height),
            targetSize: targetSize
        )
        var image = try decodeImage(source, maxPixelSize: maxPixelSize)
        if crop != CGRect(origin: .zero, size: sourceSize) {
            let scaleX = Double(image.width) / Double(sourceSize.width)
            let scaleY = Double(image.height) / Double(sourceSize.height)
            let originX = min(Int((crop.origin.x * scaleX).rounded()), image.width - 1)
            let originY = min(Int((crop.origin.y * scaleY).rounded()), image.height - 1)
            let width = min(max(1, Int((crop.width * scaleX).rounded())), image.width - originX)
            let height = min(max(1, Int((crop.height * scaleY).rounded())), image.height - originY)
            guard let croppedImage = image.cropping(to: CGRect(x: originX, y: originY, width: width, height: height)) else {
                throw CustomError.transformFailed
            }
            image = croppedImage
        }
        if targetSize != nil || options.rotate != 0 || options.flipHorizontal || options.flipVertical {
            let targetWidth = targetSize.map { Int($0.width) } ?? image.width
            let targetHeight = targetSize.map { Int($0.height) } ?? image.height
            image = try applyTransformations(image, targetWidth: targetWidth, targetHeight: targetHeight, options: options)
        }
        let outputUrl = try encodeImage(image, format: options.format, quality: options.quality)
        return TransformResult(url: outputUrl, height: image.height, width: image.width)
    }
}
