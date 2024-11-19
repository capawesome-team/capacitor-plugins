//
//  CustomError.swift
//  Plugin
//
//  Created by Robin Genz on 18.11.24.
//  Copyright © 2024 Max Lynch. All rights reserved.
//


import Foundation

public enum CustomError: Error {
    case bundleIdNotFound
    case unavailable
    case unknown
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .bundleIdNotFound:
            return NSLocalizedString("Could not open App Store because the bundle ID could not be determined.", comment: "bundleIdNotFound")
        case .unavailable:
            return NSLocalizedString("This plugin method is not available on this platform.", comment: "unavailable")
        case .unknown:
            return NSLocalizedString("An unknown error occurred.", comment: "unknown")
        }
    }
}
