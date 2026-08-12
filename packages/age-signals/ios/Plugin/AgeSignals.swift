import Foundation
import Capacitor

#if canImport(DeclaredAgeRange)
import DeclaredAgeRange
#endif

@objc public class AgeSignals: NSObject {
    let plugin: AgeSignalsPlugin

    init(plugin: AgeSignalsPlugin) {
        self.plugin = plugin
    }

    @objc func getRegulatoryRequirements(completion: @escaping (GetRegulatoryRequirementsResult?, Error?) -> Void) {
        #if canImport(DeclaredAgeRange)
        guard #available(iOS 26.2, *) else {
            completion(nil, CustomError.notSupported)
            return
        }
        Task {
            do {
                let ageAssuranceRequired = try await AgeRangeService.shared.isEligibleForAgeFeatures
                let regulatoryFeatures = try await Self.fetchRegulatoryFeatures()
                completion(GetRegulatoryRequirementsResult(
                    ageAssuranceRequired: ageAssuranceRequired,
                    regulatoryFeatures: regulatoryFeatures
                ), nil)
            } catch {
                completion(nil, Self.mapError(error))
            }
        }
        #else
        completion(nil, CustomError.notSupported)
        #endif
    }

    @objc func isAvailable(completion: @escaping (IsAvailableResult) -> Void) {
        #if canImport(DeclaredAgeRange)
        if #available(iOS 26.0, *) {
            completion(IsAvailableResult(available: true))
        } else {
            completion(IsAvailableResult(available: false))
        }
        #else
        completion(IsAvailableResult(available: false))
        #endif
    }

    @objc func requestAgeRange(_ options: RequestAgeRangeOptions, completion: @escaping (RequestAgeRangeResult?, Error?) -> Void) {
        #if canImport(DeclaredAgeRange)
        guard #available(iOS 26.0, *) else {
            completion(nil, CustomError.notSupported)
            return
        }
        Task { @MainActor in
            do {
                guard let viewController = self.plugin.bridge?.viewController else {
                    completion(nil, CustomError.presentationContextUnavailable)
                    return
                }

                let ageGates = options.ageGates
                let response: AgeRangeService.Response
                switch ageGates.count {
                case 1:
                    response = try await AgeRangeService.shared.requestAgeRange(ageGates: ageGates[0], in: viewController)
                case 2:
                    response = try await AgeRangeService.shared.requestAgeRange(ageGates: ageGates[0], ageGates[1], in: viewController)
                default:
                    response = try await AgeRangeService.shared.requestAgeRange(
                        ageGates: ageGates[0], ageGates[1], ageGates[2],
                        in: viewController
                    )
                }

                completion(Self.mapResponse(response), nil)
            } catch {
                completion(nil, Self.mapError(error))
            }
        }
        #else
        completion(nil, CustomError.notSupported)
        #endif
    }

    @objc func showSignificantUpdateAcknowledgment(
        _ options: ShowSignificantUpdateAcknowledgmentOptions,
        completion: @escaping (Error?) -> Void
    ) {
        // `showSignificantUpdateAcknowledgment` requires the iOS 26.4 SDK (Xcode 26.4, Swift 6.3).
        #if compiler(>=6.3) && canImport(DeclaredAgeRange)
        guard #available(iOS 26.4, *) else {
            completion(CustomError.notSupported)
            return
        }
        Task { @MainActor in
            do {
                guard let windowScene = self.plugin.bridge?.viewController?.view.window?.windowScene else {
                    completion(CustomError.presentationContextUnavailable)
                    return
                }

                try await AgeRangeService.shared.showSignificantUpdateAcknowledgment(
                    in: windowScene,
                    updateDescription: options.updateDescription
                )
                completion(nil)
            } catch {
                completion(Self.mapError(error))
            }
        }
        #else
        completion(CustomError.notSupported)
        #endif
    }

    #if canImport(DeclaredAgeRange)
    @available(iOS 26.2, *)
    private static func fetchRegulatoryFeatures() async throws -> [RegulatoryFeature] {
        // `requiredRegulatoryFeatures` requires the iOS 26.4 SDK (Xcode 26.4, Swift 6.3).
        #if compiler(>=6.3)
        guard #available(iOS 26.4, *) else {
            return []
        }
        return try await AgeRangeService.shared.requiredRegulatoryFeatures.compactMap { feature in
            switch feature {
            case .declaredAgeRangeRequired:
                return .declaredAgeRangeRequired
            case .significantAppChangeRequiresAdultNotification:
                return .significantAppChangeRequiresAdultNotification
            case .significantAppChangeRequiresParentalConsent:
                return .significantAppChangeRequiresParentalConsent
            default:
                return nil
            }
        }
        #else
        return []
        #endif
    }

    @available(iOS 26.0, *)
    private static func mapAgeRange(_ range: AgeRangeService.AgeRange) -> AgeRange {
        return AgeRange(
            activeParentalControls: mapParentalControls(range.activeParentalControls),
            ageRangeDeclaration: mapAgeRangeDeclaration(range.ageRangeDeclaration),
            lowerBound: range.lowerBound,
            upperBound: range.upperBound
        )
    }

    @available(iOS 26.0, *)
    private static func mapAgeRangeDeclaration(_ declaration: AgeRangeService.AgeRangeDeclaration?) -> AgeRangeDeclaration? {
        guard let declaration = declaration else {
            return nil
        }

        switch declaration {
        case .selfDeclared:
            return .selfDeclared
        case .guardianDeclared:
            return .guardianDeclared
        default:
            return mapGranularAgeRangeDeclaration(declaration)
        }
    }

    @available(iOS 26.0, *)
    private static func mapGranularAgeRangeDeclaration(
        _ declaration: AgeRangeService.AgeRangeDeclaration
    ) -> AgeRangeDeclaration? {
        if #available(iOS 26.2, *) {
            switch declaration {
            case .checkedByOtherMethod:
                return .checkedByOtherMethod
            case .governmentIDChecked:
                return .governmentIdChecked
            case .guardianCheckedByOtherMethod:
                return .guardianCheckedByOtherMethod
            case .guardianGovernmentIDChecked:
                return .guardianGovernmentIdChecked
            case .guardianPaymentChecked:
                return .guardianPaymentChecked
            case .paymentChecked:
                return .paymentChecked
            default:
                break
            }
        }

        // `.confirmed` requires the iOS 26.5 SDK (Xcode 26.5, Swift 6.3.2).
        #if compiler(>=6.3.2)
        if #available(iOS 26.5, *), declaration == .confirmed {
            return .confirmed
        }
        #endif

        return nil
    }

    @available(iOS 26.0, *)
    private static func mapError(_ error: Error) -> Error {
        guard let error = error as? AgeRangeService.Error else {
            return error
        }
        switch error {
        case .notAvailable:
            return CustomError.apiNotAvailable
        case .invalidRequest:
            return CustomError.invalidRequest
        default:
            return error
        }
    }

    @available(iOS 26.0, *)
    private static func mapParentalControls(_ controls: AgeRangeService.ParentalControls) -> [ParentalControl] {
        var result: [ParentalControl] = []
        if controls.contains(.communicationLimits) {
            result.append(.communicationLimits)
        }
        return result
    }

    @available(iOS 26.0, *)
    private static func mapResponse(_ response: AgeRangeService.Response) -> RequestAgeRangeResult {
        switch response {
        case .sharing(let range):
            return RequestAgeRangeResult(status: .shared, ageRange: mapAgeRange(range))
        default:
            // Treat `.declinedSharing` and any future case as not shared.
            return RequestAgeRangeResult(status: .notShared)
        }
    }
    #endif
}
