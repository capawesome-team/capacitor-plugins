import Foundation

enum CustomError: Error {
    case idMissing
    case imageInvalid
    case selectedColorInvalid
    case selectedTabIdInvalid
    case systemImageInvalid
    case systemImageOrImageInvalid
    case tabIdsNotUnique
    case tabNotFound
    case tabsEmpty
    case tabsMissing
    case tabsNotSet
    case titleMissing
    case unselectedColorInvalid
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .idMissing:
            return NSLocalizedString("id must be provided.", comment: "idMissing")
        case .imageInvalid:
            return NSLocalizedString("image must be the name of an image in the asset catalog.", comment: "imageInvalid")
        case .selectedColorInvalid:
            return NSLocalizedString("selectedColor must be a valid hex color code.", comment: "selectedColorInvalid")
        case .selectedTabIdInvalid:
            return NSLocalizedString("selectedTabId must be the id of one of the tabs.", comment: "selectedTabIdInvalid")
        case .systemImageInvalid:
            return NSLocalizedString("systemImage must be the name of an SF Symbol.", comment: "systemImageInvalid")
        case .systemImageOrImageInvalid:
            return NSLocalizedString("exactly one of systemImage or image must be provided.", comment: "systemImageOrImageInvalid")
        case .tabIdsNotUnique:
            return NSLocalizedString("tabs must have unique ids.", comment: "tabIdsNotUnique")
        case .tabNotFound:
            return NSLocalizedString("No tab with the given id exists.", comment: "tabNotFound")
        case .tabsEmpty:
            return NSLocalizedString("tabs must not be empty.", comment: "tabsEmpty")
        case .tabsMissing:
            return NSLocalizedString("tabs must be provided.", comment: "tabsMissing")
        case .tabsNotSet:
            return NSLocalizedString("No tabs have been set yet.", comment: "tabsNotSet")
        case .titleMissing:
            return NSLocalizedString("title must be provided.", comment: "titleMissing")
        case .unselectedColorInvalid:
            return NSLocalizedString("unselectedColor must be a valid hex color code.", comment: "unselectedColorInvalid")
        }
    }
}
