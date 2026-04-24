enum AgeRangeDeclaration: String {
    case selfDeclared = "SELF_DECLARED"
    case guardianDeclared = "GUARDIAN_DECLARED"
    case checkedByOtherMethod = "CHECKED_BY_OTHER_METHOD"
    case guardianCheckedByOtherMethod = "GUARDIAN_CHECKED_BY_OTHER_METHOD"
    case governmentIdChecked = "GOVERNMENT_ID_CHECKED"
    case guardianGovernmentIdChecked = "GUARDIAN_GOVERNMENT_ID_CHECKED"
    case paymentChecked = "PAYMENT_CHECKED"
    case guardianPaymentChecked = "GUARDIAN_PAYMENT_CHECKED"
}
