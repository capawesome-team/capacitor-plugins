import Foundation

public class IntuneHelper {
    public static func getFilePath(_ pathOrUrl: String) -> String {
        if let url = URL(string: pathOrUrl), url.isFileURL {
            return url.path
        }
        return pathOrUrl
    }
}
