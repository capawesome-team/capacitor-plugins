import Foundation

@objc public class Localization: NSObject {
    let plugin: LocalizationPlugin

    init(plugin: LocalizationPlugin) {
        self.plugin = plugin
    }

    @objc public func getLocales(completion: @escaping (GetLocalesResult?, Error?) -> Void) {
        let locales = Locale.preferredLanguages.map { createLocaleResult(identifier: $0) }
        completion(GetLocalesResult(locales: locales), nil)
    }

    @objc public func getSettings(completion: @escaping (GetSettingsResult?, Error?) -> Void) {
        let timeZone = TimeZone.current.identifier
        let uses24HourClock = determineUses24HourClock()
        let firstDayOfWeek = convertToIsoDayOfWeek(Calendar.current.firstWeekday)
        let result = GetSettingsResult(timeZone: timeZone, uses24HourClock: uses24HourClock, firstDayOfWeek: firstDayOfWeek)
        completion(result, nil)
    }

    private func convertToIsoDayOfWeek(_ weekday: Int) -> Int {
        return ((weekday + 5) % 7) + 1
    }

    private func createLocaleResult(identifier: String) -> LocaleResult {
        let locale = Locale(identifier: identifier)
        let languageCode: String?
        let regionCode: String?
        let currencyCode: String?
        if #available(iOS 16, *) {
            languageCode = locale.language.languageCode?.identifier
            regionCode = locale.region?.identifier
            currencyCode = locale.currency?.identifier
        } else {
            languageCode = locale.languageCode
            regionCode = locale.regionCode
            currencyCode = locale.currencyCode
        }
        let currencySymbol = locale.currencySymbol.flatMap { $0.isEmpty ? nil : $0 }
        let resolvedLanguageCode = languageCode ?? identifier
        return LocaleResult(
            languageTag: identifier,
            languageCode: resolvedLanguageCode,
            regionCode: regionCode,
            currencyCode: currencyCode,
            currencySymbol: currencySymbol,
            decimalSeparator: locale.decimalSeparator,
            groupingSeparator: locale.groupingSeparator,
            textDirection: resolveTextDirection(languageCode: resolvedLanguageCode),
            measurementSystem: resolveMeasurementSystem(locale: locale, regionCode: regionCode)
        )
    }

    private func determineUses24HourClock() -> Bool {
        guard let format = DateFormatter.dateFormat(fromTemplate: "j", options: 0, locale: Locale.current) else {
            return false
        }
        return !format.contains("a")
    }

    private func resolveMeasurementSystem(locale: Locale, regionCode: String?) -> String? {
        if #available(iOS 16, *) {
            switch locale.measurementSystem {
            case .metric:
                return "metric"
            case .us:
                return "us"
            case .uk:
                return "uk"
            default:
                return nil
            }
        }
        guard let regionCode = regionCode else {
            return nil
        }
        switch regionCode.uppercased() {
        case "US", "LR", "MM":
            return "us"
        case "GB":
            return "uk"
        default:
            return "metric"
        }
    }

    private func resolveTextDirection(languageCode: String) -> String {
        return Locale.characterDirection(forLanguage: languageCode) == .rightToLeft ? "rtl" : "ltr"
    }
}
