import Foundation

#if canImport(DeclaredAgeRange)
import DeclaredAgeRange
#endif

@objc public class AgeSignals: NSObject {
    let plugin: AgeSignalsPlugin

    init(plugin: AgeSignalsPlugin) {
        self.plugin = plugin
    }

    @objc func checkAgeSignals(_ options: CheckAgeSignalsOptions, completion: @escaping (CheckAgeSignalsResult?, Error?) -> Void) {
        #if canImport(DeclaredAgeRange)
        if #available(iOS 26.0, *) {
            Task { @MainActor in
                do {
                    guard let viewController = self.plugin.bridge?.viewController else {
                        completion(nil, CustomError.presentationContextUnavailable)
                        return
                    }

                    let response: AgeRangeService.Response

                    switch options.ageGates.count {
                    case 2:
                        response = try await AgeRangeService.shared.requestAgeRange(
                            ageGates: options.ageGates[0], options.ageGates[1],
                            in: viewController
                        )
                    case 3:
                        response = try await AgeRangeService.shared.requestAgeRange(
                            ageGates: options.ageGates[0], options.ageGates[1], options.ageGates[2],
                            in: viewController
                        )
                    default:
                        completion(nil, CustomError.illegalAgeGates)
                        return
                    }

                    let result = Self.mapResponseToResult(response)
                    completion(result, nil)
                } catch {
                    completion(nil, error)
                }
            }
        } else {
            completion(nil, CustomError.apiNotAvailable)
        }
        #else
        completion(nil, CustomError.apiNotAvailable)
        #endif
    }

    @objc func checkEligibility(completion: @escaping (CheckEligibilityResult?, Error?) -> Void) {
        #if canImport(DeclaredAgeRange)
        if #available(iOS 26.2, *) {
            Task { @MainActor in
                do {
                    let isEligible = try await AgeRangeService.shared.isEligibleForAgeFeatures
                    let result = CheckEligibilityResult(isEligible: isEligible)
                    completion(result, nil)
                } catch {
                    completion(nil, CustomError.apiNotAvailable)
                }
            }
        } else {
            completion(nil, CustomError.apiNotAvailable)
        }
        #else
        completion(nil, CustomError.apiNotAvailable)
        #endif
    }

    #if canImport(DeclaredAgeRange)
    @available(iOS 26.0, *)
    private static func mapResponseToResult(
        _ response: AgeRangeService.Response
    ) -> CheckAgeSignalsResult {
        switch response {
        case .declinedSharing:
            return CheckAgeSignalsResult(
                userStatus: .unknown,
                ageLower: nil,
                ageUpper: nil,
                mostRecentApprovalDate: nil,
                installId: nil
            )

        case .sharing(let range):
            let lower = range.lowerBound
            let upper = range.upperBound

            let status: UserStatus
            if let lower, lower >= 18 {
                status = .verified
            } else if let upper, upper < 18 {
                status = .supervised
            } else {
                status = .unknown
            }

            return CheckAgeSignalsResult(
                userStatus: status,
                ageLower: lower,
                ageUpper: upper,
                mostRecentApprovalDate: nil,
                installId: nil
            )

        @unknown default:
            return CheckAgeSignalsResult(
                userStatus: .empty,
                ageLower: nil,
                ageUpper: nil,
                mostRecentApprovalDate: nil,
                installId: nil
            )
        }
    }
    #endif
}
