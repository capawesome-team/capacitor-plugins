import XCTest
import ZIPFoundation
@testable import LiveUpdatePlugin

class LiveUpdateTests: XCTestCase {

    func testEcho() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

//        let implementation = LiveUpdate(config: .init(), plugin: .init())
//        let value = "Hello, World!"
//        let result = implementation.echo(value)
    }

    func testUnzipFile() {
        let config = LiveUpdateConfig()
        let plugin = LiveUpdatePlugin()
        let implementation = LiveUpdate(config: config, plugin: plugin)
        
        // Create a dummy text file for testing
        let fileManager = FileManager()
        let currentWorkingPath = fileManager.currentDirectoryPath
        var sourceURL = URL(fileURLWithPath: currentWorkingPath)
        sourceURL.appendPathComponent("test.txt")
        fileManager.createFile(atPath: sourceURL.path, contents: "test".data(using: .utf8), attributes: nil)
        
        // Create a zip archive
        var destinationURL = URL(fileURLWithPath: currentWorkingPath)
        destinationURL.appendPathComponent("test.zip")
        do {
            try fileManager.zipItem(at: sourceURL, to: destinationURL)
            
            // Unzip the file
            let unzippedDirectory = try implementation.unzipFile(zipFile: destinationURL)
            let unzippedFilePath = unzippedDirectory.appendingPathComponent("test.txt")
            XCTAssertTrue(fileManager.fileExists(atPath: unzippedFilePath.path))
            
            // Clean up
            try fileManager.removeItem(at: destinationURL)
            try fileManager.removeItem(at: unzippedDirectory)
            try fileManager.removeItem(at: sourceURL)
        } catch {
            XCTFail("Error occurred during unzipFile test: \(error)")
        }
    }
}
